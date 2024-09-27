const canvas = document.getElementById('canvas');
const context = canvas.getContext('2d');

canvas.width = 400;
canvas.height = 400;
const R = 160;
const fillColor = "lightpink";


function drawAxis() {
    context.beginPath();
    context.moveTo(0, canvas.height / 2);
    context.lineTo(canvas.width, canvas.height / 2);
    context.strokeStyle = 'black';
    context.lineWidth = 2;
    context.stroke();

    context.beginPath();
    context.moveTo(canvas.width / 2, 0);
    context.lineTo(canvas.width / 2, canvas.height);
    context.strokeStyle = 'black';
    context.lineWidth = 2;
    context.stroke();
}

function drawGrid() {
    context.beginPath();
    for (let x = 0; x < 400; x += 40) {
        context.moveTo(x, 0);
        context.lineTo(x, canvas.height);
        context.strokeStyle = 'lightgray';
        context.lineWidth = 1;
        context.stroke();
    }
    for (let y = 0; y < 400; y += 40) {
        context.moveTo(0, y);
        context.lineTo(canvas.width, y);
        context.strokeStyle = 'lightgray';
        context.lineWidth = 1;
        context.stroke();
    }
}

function drawCircle(x, y, r) {
    context.beginPath();
    context.moveTo(x, y);
    context.fillStyle = fillColor;
    context.arc(x, y, r, 0, Math.PI/2, false);
    context.closePath();
    context.fill();
}

function drawRect(x, y, w, h) {
    context.beginPath();
    context.moveTo(x, y);
    context.fillStyle = fillColor;
    context.rect(x, y, w, h);
    context.closePath();
    context.fill();
}

function drawTriangle(x1, y1, x2, y2, x3, y3) {
    context.beginPath();
    context.moveTo(x1, y1);
    context.fillStyle = fillColor;
    context.lineTo(x2, y2);
    context.lineTo(x3, y3);
    context.closePath();
    context.fill();
}

function drawCoords() {
	const centerX = canvas.width / 2;
	const centerY = canvas.height / 2;
	const gap = 30;
	
    context.fillStyle = 'black';
    context.font = '1.25em Montserrat, sans-serif';
    context.textAlign = 'center';
    context.textBaseline = 'bottom';
	
    context.fillText('R/2', centerX + R/2, centerY + gap);
    context.beginPath();
    context.moveTo(centerX + R, centerY - 5);
    context.lineTo(centerX + R, centerY + 5);
    context.strokeStyle = 'black';
    context.closePath();
    context.stroke();
    context.fillText('R', centerX + R, centerY + gap);
    context.beginPath();
    context.moveTo(centerX + R, centerY - 5);
    context.lineTo(centerX + R, centerY + 5);
    context.strokeStyle = 'black';
    context.stroke();
    context.closePath();
    context.fillText('-R/2', centerX + gap, centerY + R/2 + 10);
    context.beginPath();
    context.moveTo(centerX - 5, centerY + R/2);
    context.lineTo(centerX + 5, centerY + R/2);
    context.strokeStyle = 'black';
    context.closePath();
    context.stroke();
    context.fillText('-R', centerX + gap - 0, centerY + R + 10);
    context.beginPath();
    context.moveTo(centerX - 5, centerY + R);
    context.lineTo(centerX + 5, centerY + R);
    context.strokeStyle = 'black';
    context.closePath();
    context.stroke();
    context.beginPath();
    context.fillText('-R/2', centerX - R/2, centerY + gap);
    context.moveTo(centerX - R/2, centerY - 5);
    context.lineTo(centerX - R/2, centerY + 5);
    context.strokeStyle = 'black';
    context.closePath();
    context.stroke();
    context.beginPath();
    context.fillText('-R', centerX - R, centerY + gap);
    context.moveTo(centerX - R, centerY - 5);
    context.lineTo(centerX - R, centerY + 5);
    context.strokeStyle = 'black';
    context.closePath();
    context.stroke();
    context.beginPath();
    context.fillText('R/2', centerX + gap, centerY - R/2 + 10);
    context.moveTo(centerX - 5, centerY - R/2);
    context.lineTo(centerX + 5, centerY - R/2);
    context.strokeStyle = 'black';
    context.closePath();
    context.stroke();
    context.beginPath();
    context.fillText('R', centerX + gap, centerY - R + 10);
    context.moveTo(centerX - 5, centerY - R);
    context.lineTo(centerX + 5, centerY - R);
    context.strokeStyle = 'black';
    context.closePath();
    context.stroke();

}

drawGrid();
drawCircle(canvas.width / 2, canvas.height / 2, R);
drawRect(canvas.width / 2, canvas.height / 2, -R / 2, -R);
drawTriangle(canvas.width / 2, canvas.height / 2, canvas.width / 2, canvas.height / 2 + R, canvas.width / 2 - R, canvas.height / 2);
drawAxis();
drawCoords();