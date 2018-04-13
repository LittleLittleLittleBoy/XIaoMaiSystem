
var currentChoose=0;

function createRoom() {
    var rows=document.getElementById("roomRows").value;
    var cols=document.getElementById("roomCols").value;

    var root=document.getElementById("seatPanel");
    while(root.firstChild) {
        root.removeChild(root.firstChild);
    }

    for (var i=0;i<rows;i++){
        var rowTemp=document.createElement("div");
        for(var j=0;j<cols;j++){
            var seatTemp=document.createElement("button");
            seatTemp.className="btn seatButton";
            seatTemp.value="0";
            seatTemp.onclick=function (event) {
                changeType(event.srcElement)
            };
            rowTemp.appendChild(seatTemp);
        }
        root.appendChild(rowTemp);
        console.log(rowTemp);
    }
}

function changeChoose(index) {
    currentChoose=index;
    if (index===0){
        document.getElementById("emptyChoose").innerHTML="✓";
    }else{
        document.getElementById("emptyChoose").innerHTML="";
    }
    if (index===1){
        document.getElementById("primaryChoose").innerHTML="✓";
    }else{
        document.getElementById("primaryChoose").innerHTML="";
    }
    if (index===2){
        document.getElementById("intermediateChoose").innerHTML="✓";
    }else{
        document.getElementById("intermediateChoose").innerHTML="";
    }
    if (index===3){
        document.getElementById("advancedChoose").innerHTML="✓";
    }else{
        document.getElementById("advancedChoose").innerHTML="";
    }

}

function changeType(obj) {
    obj['value']=currentChoose;

    switch (obj['value']){
        case '0':
            obj.style['backgroundColor']='lightgray';
            break;
        case '1':
            obj.style['backgroundColor']='gray';
            break;
        case '2':
            obj.style['backgroundColor']='coral';
            break;
        case '3':
            obj.style['backgroundColor']='indianred';
            break;
    }
    console.log(obj['value'])
}

function saveRoom() {
    var root=document.getElementById("seatPanel");

    var lines=root.children;
    var result="";
    for (var i=0;i<lines.length;i++){
        var line=lines[i].children;
        for (var j=0;j<line.length;j++){
            result+=line[j].value;
        }
        result+=';'
    }
    console.log(result);
    var roomName=$('#roomNameInput').val();
    $.post("/theater/addNewRoom",{
        "roomid":roomName,
        "roominfo":result,
        "row":lines.length,
        "col":lines[0].children.length
    },function (result) {

        alert(result)
    })
    //return result;
}
function deleteRoom(roomid){
    $.post("/theater/deleteRoom",{
        "roomid":roomid,
    },function (result) {
        alert("请等待管理员确认")
    })
}
function displayRoom(roominfo) {
    var rooms=roominfo.split(';');
    var root=document.getElementById("showRooms");
    while(root.firstChild) {
        root.removeChild(root.firstChild);
    }
    var title=document.createElement("div");
    title.innerHTML="<div>\n" +
        "            <button id=\"emptyChoose\" class=\"btn signButton\" disabled='true' style=\"background-color: lightgray\"></button>Empty\n" +
        "            <button id=\"primaryChoose\" class=\"btn signButton\" disabled='true' style=\"background-color: gray\"></button>Primary seats\n" +
        "            <button id=\"intermediateChoose\" class=\"btn signButton\" disabled='true' style=\"background-color: coral\"></button>Intermediate seats\n" +
        "            <button id=\"advancedChoose\" class=\"btn signButton\" disabled='true' style=\"background-color: indianred\"></button>Advanced seats\n" +
        "        </div>";
    root.appendChild(title);

    function getColor(param) {
        switch (param){
            case '0':
                return 'lightgray';
                break;
            case '1':
                return 'gray';
                break;
            case '2':
                return 'coral';
                break;
            case '3':
                return 'indianred';
                break;
        }
    }

    for(var i=0;i<rooms.length-1;i++){
        var rowTemp=document.createElement("div");
        for(var j=0;j<rooms[i].length;j++){
            var seatTemp=document.createElement("button");
            seatTemp.className="btn seatButton";
            seatTemp.value=rooms[i].charAt(j);
            seatTemp.style['backgroundColor']=getColor(rooms[i].charAt(j));
            rowTemp.appendChild(seatTemp);
        }
        root.appendChild(rowTemp);
        console.log(rowTemp);
    }
}
