let bird = document.getElementById("bird");
let pipeContainer = document.getElementById("pipe-container");
let pipes = [];
let score = 0;
let isGameOver = false;
let isPaused = false;

let birdX = 50;
let birdY = 150;
let birdVelocityY = 0;   //vận tốc 
const gravity = 0.6;   //ban đầu để trọng lực bằng 0 , chim chưa rơi
const lift = -5; // Tốc độ chim bay lên

// Lấy điểm số và vị trí chim từ sessionStorage
const sessionScore = parseInt(sessionStorage.getItem('score')) || 0;
const birdStartPosition = parseInt(sessionStorage.getItem('birdPosition')) || 150;

// Đặt lại chim và điểm số khi tiếp tục chơi
bird.style.top = birdStartPosition + "px";

document.getElementById("score-value").innerText = sessionScore;

// Cập nhật birdY
function updateBird() {
    if (isPaused || isGameOver) return;

    birdVelocityY += gravity;
    birdY += birdVelocityY;
    bird.style.top = birdY + "px";
    bird.style.left = birdX + "px";

    if (birdY > 570 || birdY < 0) {
        isGameOver = true;
        gameOver(score, birdY);
    }
}

function createPipe() {
    const pipeGap = Math.floor(Math.random() * 50) + 120;   
	//làm tròn lên 1 số trong khoảng 0-49.999 rồi + 120
	
    const pipeHeight = Math.floor(Math.random() * 200) + 100; 
	
	//khai báo biến chiều cao là làm tròn lên , random trong khoảng 200 rồi + 100 

    const toppipe = document.createElement("img");
    toppipe.src = "toppipe.png";  //thiết lập thuộc tính src
    toppipe.className = "pipe toppipe"; //thiết lập className
    toppipe.style.height = pipeHeight + "px"; 
    toppipe.style.left = "400px";
    toppipe.style.position = "absolute";
    pipeContainer.appendChild(toppipe);

    const bottompipe = document.createElement("img");
    bottompipe.src = "bottompipe.png";
    bottompipe.className = "pipe bottompipe";
    bottompipe.style.height = (600 - pipeHeight - pipeGap) + "px";
    bottompipe.style.left = "400px";
    bottompipe.style.position = "absolute";
    bottompipe.style.bottom = "0px";
    pipeContainer.appendChild(bottompipe);

    pipes.push({ top: toppipe, bottom: bottompipe });
}

function updatePipes() {
    if (isPaused || isGameOver) return;
//kiểm tra điều kiện khi dừng game hoặc kt trò chơi
    pipes.forEach(pipe => {  //chạy qua từng ống trong mảng pipe 
        const topPipe = pipe.top;
        const bottomPipe = pipe.bottom;

        let pipeX = parseInt(topPipe.style.left.replace("px", ""));
		//lấy gtri nguyen của left bỏ đuôi px 
        pipeX -= 2;  
		//pipeX có là chiều rộng của ống 
        topPipe.style.left = pipeX + "px";
        bottomPipe.style.left = pipeX + "px";

        if (pipeX + 50 < 0) {
            topPipe.remove();
            bottomPipe.remove();
            pipes.shift();
            score++;
            sessionStorage.setItem('score', score);
            document.getElementById("score-value").innerText = score;
        }

        if ( //kt va chạm 
            (birdY < topPipe.offsetHeight && pipeX < birdX + bird.width && pipeX + 50 > birdX) ||
            (birdY + bird.height > bottomPipe.offsetTop && pipeX < birdX + bird.width && pipeX + 50 > birdX)
        ) {
            isGameOver = true;
            gameOver(score, birdY);
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
    const countdownElement = document.createElement("div");
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
    const countdownInterval = setInterval(() => {
        countdownValue--;
        countdownElement.innerText = countdownValue;
        if (countdownValue === 0) {
            clearInterval(countdownInterval);
            document.body.removeChild(countdownElement);
            createPipe();
            setInterval(createPipe, 3000);
            draw();
        }
    }, 1000);
}

function restartGame() {
    pipes.forEach(pipe => {
        pipe.top.remove();
        pipe.bottom.remove();
    });
    pipes = [];
    birdY = birdStartPosition;
    birdVelocityY = 0;
    score = sessionScore;
    isGameOver = false;
    isPaused = false;
    document.getElementById("resumeButton").style.display = "none";
    document.getElementById("pauseButton").style.display = "block";
    startGame();
}

function startGame() {
    birdVelocityY = 0; // Dừng chim rơi trong lúc đếm ngược
    countdown(() => {
        draw();
    });
}

function pauseGame() {
    isPaused = true;
}

function resumeGame() {
    isPaused = false;
    birdVelocityY = 0; // Đặt lại vận tốc chim để tránh rơi đột ngột
    draw(); // Tiếp tục trò chơi
}

document.addEventListener("keydown", function (e) {
    if (e.code === "Space" && !isGameOver && !isPaused) {
        birdVelocityY = lift; // Chim bay lên khi nhấn Space
    } else if (e.code === "Space" && isGameOver) {
        restartGame(); // Khởi động lại khi trò chơi kết thúc
    } else if (e.code === "KeyH" && !isGameOver) {
        pauseGame(); // Dừng trò chơi khi nhấn H
    } else if (e.code === "KeyD" && isPaused && !isGameOver) {
        resumeGame(); // Tiếp tục trò chơi khi nhấn D
    }
});

document.addEventListener("DOMContentLoaded", function () {
    const playerName = localStorage.getItem("username") || "Unknown Player";
    document.getElementById("player").innerText = playerName;
});

function gameOver(score, birdY) {
    sessionStorage.setItem('score', score);
    sessionStorage.setItem('birdPosition', birdY);

    window.location.href = `gameover.html?score=${score}`;
}

startGame(); // Bắt đầu trò chơi ngay khi trang tải
