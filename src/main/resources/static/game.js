let bird = document.getElementById("bird");
let pipeContainer = document.getElementById("pipe-container");
let pipes = [];
let score = 0;
let isGameOver = false;
let isPaused = false;

// Tạo ống mới
function createPipe() {
    let pipeGap = Math.floor(Math.random() * 50) + 120; // Tạo khoảng cách ngẫu nhiên từ 120 đến 170
    let pipeHeight = Math.floor(Math.random() * 200) + 100; // Chiều cao ống ngẫu nhiên từ 100 đến 300

    let toppipe = document.createElement("img");
    toppipe.src = "toppipe.png";
    toppipe.className = "pipe toppipe";
    toppipe.style.height = pipeHeight + "px";
    toppipe.style.left = "400px";
    toppipe.style.position = "absolute";
    pipeContainer.appendChild(toppipe);

    let bottompipe = document.createElement("img");
    bottompipe.src = "bottompipe.png";
    bottompipe.className = "pipe bottompipe";
    bottompipe.style.height = (600 - pipeHeight - pipeGap) + "px";
    bottompipe.style.left = "400px";
    bottompipe.style.position = "absolute";
    bottompipe.style.top = (pipeHeight + pipeGap) + "px";
    pipeContainer.appendChild(bottompipe);

    pipes.push({ top: toppipe, bottom: bottompipe });
}

// Vị trí ban đầu của con chim
let birdX = 50;
let birdY = 150;
let birdVelocityY = 0;
const gravity = 0.6;
const lift = -5; // Giảm biên độ dịch chuyển lên trên

function updateBird() {
    if (isPaused || isGameOver) return;

    birdVelocityY += gravity;
    birdY += birdVelocityY;
    bird.style.top = birdY + "px";
    bird.style.left = birdX + "px";

    if (birdY > 570 || birdY < 0 || birdX > 370 || birdX < 0) {
        isGameOver = true;
        gameOver();
    }
}

// Cập nhật điểm
function updateScore() {
    document.getElementById("score").innerText = score;
}

// Chơi lại trò chơi
function restartGame() {
    pipes.forEach(pipe => {
        pipe.top.remove();
        pipe.bottom.remove();
    });
    pipes = [];
    birdY = 150;
    birdX = 50;
    birdVelocityY = 0;
    score = 0;
    isGameOver = false;
    isPaused = false;
    document.getElementById("resumeButton").style.display = "none";
    document.getElementById("pauseButton").style.display = "block";
    startGame();
}

// Xử lý sự kiện nhấn phím
document.addEventListener("keydown", function (e) {
    if (e.code === "Space" && !isGameOver && !isPaused) {
        birdVelocityY = lift;
    } else if (e.code === "Space" && isGameOver) {
        restartGame();
    } else if (e.code === "KeyP" && !isGameOver && !isPaused) {
        birdX += 20; // Di chuyển chim sang phải
    } else if (e.code === "KeyW" && !isGameOver && !isPaused) {
        birdX -= 20; // Di chuyển chim sang trái
    }
});

// Cập nhật và di chuyển các ống
function updatePipes() {
    if (isPaused || isGameOver) return;

    pipes.forEach(pipe => {
        let topPipe = pipe.top;
        let bottomPipe = pipe.bottom;

        let pipeX = parseInt(topPipe.style.left.replace("px", ""));
        pipeX -= 2;
        topPipe.style.left = pipeX + "px";
        bottomPipe.style.left = pipeX + "px";

        if (pipeX + 50 < 0) {
            topPipe.remove();
            bottomPipe.remove();
            pipes.shift();
            score++;
            updateScore();
        }

        if (
            (birdY < topPipe.offsetHeight && pipeX < birdX + bird.width && pipeX + 50 > birdX) ||
            (birdY + bird.height > bottomPipe.offsetTop && pipeX < birdX + bird.width && pipeX + 50 > birdX)
        ) {
            isGameOver = true;
            gameOver();
        }
    });
}

// Vẽ tất cả các đối tượng
function draw() {
    if (isPaused || isGameOver) return;
    updateBird();
    updatePipes();
    requestAnimationFrame(draw);
}

// Đếm ngược trước khi bắt đầu trò chơi
function countdown(callback) {
    let countdownElement = document.createElement("div");
    countdownElement.id = "countdown";
    countdownElement.style.position = "absolute";
    countdownElement.style.top = "50%";
    countdownElement.style.left = "50%";
    countdownElement.style.transform = "translate(-50%, -50%)";
    countdownElement.style.fontSize = "48px";
    countdownElement.style.color = "white";
    countdownElement.innerText = "3";
    document.body.appendChild(countdownElement);

    let countdownValue = 3;
    let countdownInterval = setInterval(() => {
        countdownValue--;
        countdownElement.innerText = countdownValue;
        if (countdownValue === 0) {
            clearInterval(countdownInterval);
            document.body.removeChild(countdownElement);
            callback();
        }
    }, 1000);
}

// Bắt đầu trò chơi
function startGame() {
    countdown(() => {
        createPipe();
        setInterval(createPipe, 3000);
        draw();
    });
}

// Tạm dừng trò chơi
function pauseGame() {
    isPaused = true;
    document.getElementById("pauseButton").style.display = "none";
    document.getElementById("resumeButton").style.display = "block";
}

// Tiếp tục trò chơi
function resumeGame() {
    isPaused = false;
    document.getElementById("pauseButton").style.display = "block";
    document.getElementById("resumeButton").style.display = "none";
    draw();
}

// Chuyển đến trang gameover.html khi trò chơi kết thúc
function gameOver() {
    window.location.href = "gameover.html";
}

// Sự kiện cho nút "Pause"
document.getElementById("pauseButton").addEventListener("click", pauseGame);

// Sự kiện cho nút "Resume"
document.getElementById("resumeButton").addEventListener("click", resumeGame);

// Khởi động trò chơi
startGame();
