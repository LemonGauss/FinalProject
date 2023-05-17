//
//
//
// var change=function(data)
// {
//     var table = document.getElementById("myTable");
//     var rows = table.rows;
//     for (var i = 1; i < rows.length; i++)
//     {
//         var cells = rows[i].cells;
//         if (cells.length > 4)
//         {
//             cells[2].innerHTML = "<a href=’#’>查看</a>";
//             cells[2].style.color="blue";
//             cells[3].innerHTML="未分配";
//             cells[3].style.color="red";
//             cells[4].innerHTML = "<a href=’#’>查看</a>";
//             cells[4].style.color="blue";
//         }
//     }
// }
const tbody = document.querySelector('#myTable tbody');
fetch('/getResume')
    .then(response => response.json())
    .then(data => {
        // 遍历数据数组，为每个对象创建一行表格，并将数据写入单元格中
        data.forEach(item => {
            const tr = document.createElement('tr');
            const tdRNO = document.createElement('td');
            const tdRName = document.createElement('td');
            const tdOrigin = document.createElement('td');
            const tdCheck=document.createElement('td');
            const tdState=document.createElement('td');
            tdRNO.innerHTML = item.rno;
            tdRName.innerHTML = item.rname;
            tdState.innerHTML="未分配";
            tdState.style.color="red";
            tdOrigin.innerHTML="<a href='/'>查看原简历</a>"
            tdCheck.innerHTML="<a href='/'>查看分配结果</a>"
            tr.appendChild(tdRNO);
            tr.appendChild(tdRName);
            tr.appendChild(tdOrigin);
            tr.appendChild(tdState);
            tr.appendChild(tdCheck);
            tbody.appendChild(tr);
            displayTable();
            displayPagination()
        });
    })
    .catch(error => {
        console.error(error);
    });

var table = document.getElementById("myTable");
var rowsPerPage = 12;
var currentPage = 1;
var totalPages = Math.ceil((table.rows.length - 1) / rowsPerPage);

function displayTable() {
    var startIndex = (currentPage - 1) * rowsPerPage;
    var endIndex = startIndex + rowsPerPage;
    var rows = table.rows;
    for (var i = 1; i < rows.length; i++) {
        if (i < startIndex || i >= endIndex) {
            rows[i].style.display = "none";
        } else {
            rows[i].style.display = "";
        }
    }
}

function displayPagination() {

    var pagination = document.getElementById("pagination");
    var html = "";
    html += "<a href='#' onclick='if(currentPage>1){setCurrentPage(currentPage-1);}'>" + '<' + "</a>";
    for (var i = 1; i <= 2; i++) {
        html += "<a href='#' onclick='setCurrentPage(" + i + ")'>" + i + "</a>";
    }
    html += "<a href='#' onclick='if(currentPage<Math.ceil((table.rows.length - 1) / rowsPerPage)){setCurrentPage(currentPage+1);}'>" + '>' + "</a>";
    pagination.innerHTML = html;
    pagination.children[currentPage].classList.add("active");
}

function setCurrentPage(page) {
    var pagination = document.getElementById("pagination");
    pagination.children[currentPage].classList.remove("active");
    currentPage = page;
    pagination.children[currentPage].classList.add("active");
    displayTable();
}


