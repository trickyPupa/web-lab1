const url = "/fcgi-bin/server.jar"

function createError(message) {
    document.querySelectorAll(".error").forEach(el => el.remove());
    const error = document.createElement("p");
    error.className = "error";
    error.textContent = message;
    document.getElementById("coordInputs").prepend(error);
}

const checkX = (value) => {
    return new Promise((resolve, reject) => {
        if ((-3) > value.value || value.value > 5) {
            value.classList.add("wrong");
            reject("x вне допустимых значений");
        } else {
            resolve();
        }
    });
}

function submitForm(event) {
    event.preventDefault();
    const x = document.getElementById("x");
    const y = document.querySelector('.btn.active');
    const r = document.querySelector('.r-checkbox:checked');
    if (!x || !y || !r) {
        createError("x не определен");
    } else {
        y.classList.remove("wrong");
        r.classList.remove("wrong");
        Promise.all([
            checkX(x)
        ]).then(() =>{
            sendData(x, y, r);
        }).catch((error) => {
            createError(error);
        });
    }
}

function sendData(x, y, r) {
    fetch(url + `?x=${x.value}&y=${y.value}&r=${r.value}`).then(response => {
        response.json()
            .then(data => {
                addToTable(x.value, y.value, r.value, data.status, data.time, new Date().toLocaleTimeString());
                console.log("row added");
                drawDot(x.value, y.value, r.value, data.status);
                y.classList.remove("wrong");
                r.classList.remove("wrong");
            });
    });
}

function addToTable(x, y, r, status, time, data) {
    const tableBody = document.getElementById("records-body");
    const row = document.createElement("tr");
    row.className = "row";
    const xtd = document.createElement("td");
    xtd.className = "item";
    xtd.textContent = x;
    row.appendChild(xtd);
    const ytd = document.createElement("td");
    ytd.className = "item";
    ytd.textContent = y;
    row.appendChild(ytd);
    const rtd = document.createElement("td");
    rtd.className = "item";
    rtd.textContent = r;
    row.appendChild(rtd);
    const stattd = document.createElement("td");
    stattd.className = "item";
    stattd.textContent = status;
    row.appendChild(stattd);
    const timetd = document.createElement("td");
    timetd.className = "item";
    timetd.textContent = time;
    row.appendChild(timetd);
    const datetd = document.createElement("td");
    datetd.className = "item";
    datetd.textContent = data;
    row.appendChild(datetd);
    tableBody.prepend(row);
}

function drawDot(x, y, r, status) {
    const canvas = document.getElementById('canvas');
    const ctx = canvas.getContext('2d');
    const formula = (coord, radius) => (200 + (4 * coord * 40) / radius);
    ctx.beginPath();
    ctx.fillStyle = status ? '#A8E4A0' : '#EE204D';
    ctx.moveTo(200, 200);
    ctx.arc(formula(x, r), formula(-y, r), 5, 0, 2 * Math.PI);
    ctx.fill();
    ctx.closePath();
    console.log("dot has been drawn");
}

function toggleActive(element, cls) {
    const elements = document.querySelectorAll(cls);
    elements.forEach(el => el.classList.remove('active'));

    element.classList.add('active');
}

function checkOnly(checkbox, cls) {
    const checkboxes = document.querySelectorAll(cls);

    checkboxes.forEach((chk) => {
        if (chk !== checkbox) {
            chk.checked = false;
        }
    });
}

// addToTable(1, 1, 1, true, 12, 'now');