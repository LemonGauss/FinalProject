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
            var i=0
            var sum=0
        // 遍历数据数组，为每个对象创建一行表格，并将数据写入单元格中

        data.forEach(item => {
            i++;
            const tr = document.createElement('tr');
            const tdRNO = document.createElement('td');
            const tdRName = document.createElement('td');
            const tdOrigin = document.createElement('td');
            const tdCheck=document.createElement('td');
            const tdState=document.createElement('td');
            const tdPosition=document.createElement('td');
            const tdDownload=document.createElement('td');

            tdRNO.innerHTML = i;
            tdRName.innerHTML = item.rname;
            tdState.innerHTML="未分配";
            tdState.style.color="red";
            tdPosition.innerHTML=item.position
            // 获取最后一个点的索引
            const lastDotIndex = tdPosition.innerHTML.lastIndexOf('.');
            var fileFormat
            if (lastDotIndex !== -1) {
                // 使用substring方法截取最后一个点之后的部分
                 fileFormat = "/"+tdPosition.innerHTML.substring(lastDotIndex + 1);
                console.log(fileFormat); //
            }
            else {
                console.log('无文件格式');
            }
            tdOrigin.innerHTML = "<a href='#' onclick='sendDataToBackend(\"" + tdPosition.innerHTML + "\")'>查看原简历</a>";
            tdDownload.innerHTML = "<a href='#' onclick='downloadFile(\"" + tdPosition.innerHTML + "\")'>下载原简历</a>";
            tdCheck.innerHTML="<a href='/'>查看分配结果</a>"
            tr.appendChild(tdRNO);
            tr.appendChild(tdRName);
            tr.appendChild(tdOrigin);
            tr.appendChild(tdDownload);
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
    for (var i = 1; i <=Math.ceil((table.rows.length - 1) / rowsPerPage); i++) {
        html += "<a href='#' onclick='setCurrentPage(" + i + ")'>" + i + "</a>";
    }
    html += "<a href='#' onclick='if(currentPage<=Math.ceil((table.rows.length - 1) / rowsPerPage)){setCurrentPage(currentPage+1);}'>" + '>' + "</a>";
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
function sendDataToBackend(tdPosition) {
    var url = "/fileToImage";  // 数据发送的路径
    var data = { position: tdPosition };  // 要发送的数据对象
    // 在这里执行发送数据到后端的逻辑
    // 例如使用 fetch 或其他 AJAX 方法发送数据
    // 示例中使用了 fetch 方法发送一个 POST 请求
    fetch(url, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(data)
    })
        .then(response => response.blob()) // 获取响应的 Blob 对象
        .then(blob => {
            var closeButton = document.createElement('span');
            closeButton.className = 'close-button';
            closeButton.innerHTML = '&times;'; // × 符号表示关闭
            // 为关闭按钮添加点击事件，点击时关闭浮窗层
            closeButton.addEventListener('click', function () {
                enablePageLinks
                document.body.removeChild(popupContainer);
            });
// 重新启用页面的超链接交互
            function enablePageLinks() {
                var links = document.querySelectorAll('a');
                for (var i = 0; i < links.length; i++) {
                    links[i].addEventListener('click', handleLinkClick);
                }
            }

            var url = URL.createObjectURL(blob);
            var imageElement = document.createElement('img');
            imageElement.className = 'imgContainer';
            imageElement.src = url; // 将 Blob 对象转为 URL，并设置给 img 标签的 src 属性显示图片
            // 创建浮窗层容器
            var popupContainer = document.createElement('div');
            popupContainer.className = 'popup-container';
            // 设置其他样式属性
            popupContainer.style.position = 'absolute';
            popupContainer.style.width = '40%';
            popupContainer.style.height = '100%';
            popupContainer.style.top='350px';
            popupContainer.style.left='800px';
            popupContainer.style.transform = 'translate(-50%, -50%)';
            popupContainer.style.backgroundColor = 'rgba(168, 162, 162, 0.8)';
            popupContainer.style.padding = '10px';
            popupContainer.style.zIndex = '9999';
            // 设置其他样式属性
            /* 图像样式 */
            imageElement.style.width ='100%';
            imageElement.style.height ='100%';
            closeButton.style.position= 'absolute';
            closeButton.style.height='70%'
            closeButton.style.top= '0px';
            closeButton.style.right= '1px';
            closeButton.style.fontSize ='30px';
            closeButton.style.color='black';
            closeButton.style.cursor='pointer';
            // 将图像元素添加到浮窗层容器中
            popupContainer.innerHTML = '';

            popupContainer.appendChild(imageElement);
            // 将关闭按钮添加到浮窗层容器中
            popupContainer.appendChild(closeButton);
            // 添加浮窗层容器到页面中
            document.body.appendChild(popupContainer);
        })
        .catch(error => {
            console.error(error);
        });
}
function downloadFile(tdPosition) {//下载源文件
    fetch('/test-download', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({ tdPosition: tdPosition })
    })
        .then(response => {
            // 检查响应状态码
            if (!response.ok) {
                throw new Error('Failed to download file');
            }
            // 解析响应头获取文件名
            const contentDisposition = response.headers.get('content-disposition');
            const fileName = contentDisposition.split('filename=')[1];

            // 将响应数据转换为 Blob
            return response.blob().then(blob => ({ fileName, blob }));
        })
        .then(({ fileName, blob }) => {
            // 创建临时的下载链接
            const url = URL.createObjectURL(blob);

            // 创建下载链接的虚拟元素
            const link = document.createElement('a');
            link.href = url;
            link.download = fileName;

            // 模拟点击下载链接
            link.click();

            // 清除临时链接
            URL.revokeObjectURL(url);
        })
        .catch(error => {
            console.error(error);
        });
}



