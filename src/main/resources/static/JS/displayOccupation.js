
const tbody = document.querySelector('#myTableO tbody');
fetch('/getOccupation')
    .then(response => response.json())
    .then(data => {
        // 遍历数据数组，为每个对象创建一行表格，并将数据写入单元格中
        data.forEach(item => {
            const tr = document.createElement('tr');
            const tdRNO = document.createElement('td');
            const tdRName = document.createElement('td');
            const tdSTime = document.createElement('td');
            const tdETime = document.createElement('td');
            const tdCheck=document.createElement('td');
            const tdCompany=document.createElement('td');
            tdRNO.innerHTML = item.pno;
            tdRName.innerHTML = item.pname;
            tdCompany.innerHTML=item.company;
            tdSTime.innerHTML=item.stime;
            tdETime.innerHTML=item.etime;
            tdCheck.innerHTML="<a href='/'>查看</a> &nbsp &nbsp <a href='/'>移除</a>"
            tr.appendChild(tdRNO);
            tr.appendChild(tdRName);
            tr.appendChild(tdCompany);
            tr.appendChild(tdSTime);
            tr.appendChild(tdETime);
            tr.appendChild(tdCheck);
            tbody.appendChild(tr);
        });
    })
    .catch(error => {
        console.error(error);
    });


var checkODetails=function ()
{

}


