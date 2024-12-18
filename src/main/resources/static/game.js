let bird = document.getElementById("bird");
let pipeContainer = document.getElementById("pipe-container");
let pipes = [];
let score = 0;
let isGameOver = false;
let isPaused = false;

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

    if (birdY > 570 || birdY < 0) {
        isGameOver = true;
        gameOver(score, birdY);  // Truyền điểm số và vị trí chim
    }
}

function updateScore() {
    document.getElementById("score-value").innerText = score;
}

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

document.addEventListener("keydown", function (e) {
    if (e.code === "Space" && !isGameOver && !isPaused) {
        birdVelocityY = lift;
    } else if (e.code === "Space" && isGameOver) {
        restartGame();
    }
});

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
            gameOver(score, birdY);  // Truyền điểm số và vị trí chim
        }
    });
}

function draw() {
    if (isPaused || isGameOver) return;
    updateBird();
    updatePipes();
    requestAnimationFrame(draw);
}

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

function startGame() {
    birdVelocityY = 0; // Dừng chim rơi trong lúc đếm ngược
    countdown(() => {
        createPipe();
        setInterval(createPipe, 3000);
        draw();
    });
}

function pauseGame() {
    isPaused = true;
    document.getElementById("pauseButton").style.display = "none";
    document.getElementById("resumeButton").style.display = "block";
}

function resumeGame() {
    isPaused = false;
    document.getElementById("pauseButton").style.display = "block";
    document.getElementById("resumeButton").style.display = "none";
    draw();
}

function gameOver(score, birdPosition) {
    // Lưu điểm số và vị trí chim vào sessionStorage
    sessionStorage.setItem('score', score);
    sessionStorage.setItem('birdPosition', birdPosition);
    isPaused = true; // Dừng các hoạt động trước khi đếm ngược
    window.location.href = 'gameover.html?score=' + score + '&birdPosition=' + birdPosition;
}

// Lấy điểm số và vị trí chim từ URL (khi tiếp tục trò chơi)
const params = new URLSearchParams(window.location.search);
const previousScore = parseInt(params.get('score')) || 0;
const birdPosition = parseInt(params.get('birdPosition')) || 150;

// Cập nhật điểm số
updateScore(previousScore);

// Đặt lại vị trí chim
bird.style.top = birdPosition + "px";

startGame(); // Bắt đầu trò chơi ngay khi tải xong
