function getColor(param) {
    switch (param){
        case 0:
            return 'inherit';
            break;
        case 1:
            return 'gray';
            break;
        case 2:
            return 'coral';
            break;
        case 3:
            return 'indianred';
            break;
    }
}
function addSeat(informtion) {
    var root=$("#seatInformtion")[0];
    if (root.childNodes.length>5){
        return false;
    }else{
        var newNode=document.createElement("p");
        newNode.innerHTML=informtion;
        root.appendChild(newNode);
        return true;
    }
}

function deleteSeat(information) {
    var root=$("#seatInformtion")[0];
    var chidren=root.childNodes;
    for (var i=0;i<chidren.length;i++){
        if (chidren.item(i).innerHTML===information){
            root.removeChild(chidren.item(i));
            break;
        }
    }
}

function toInformation(value) {
    var list=value.split(',');
    return list[0]+"排"+list[1]+'座';
}
function toValue(information) {
    var list=information.split(/排|座/);
    return list[0]+","+list[1]
}

function getPrice() {
    var price1=$("#price1")[0].innerHTML;
    var price2=$("#price2")[0].innerHTML;
    var price3=$("#price1")[0].innerHTML;
    var total=0;
    var root=$("#seatInformtion")[0];
    var chidren=root.childNodes;
    var seat=$("#seatInfo")[0].childNodes;
    for (var i=0;i<chidren.length;i++){
        var list=chidren.item(i).innerHTML.split(/排|座/);
        var temp=seat.item((parseInt(list[0]))).childNodes.item((parseInt(list[1])-1));
        switch (temp.style.backgroundColor){
            case 'gray':
                total+=parseFloat(price1);
                break;
            case 'coral':
                total+=parseFloat(price2);
                break;
            case 'indianred':
                total+=parseFloat(price3);
        }
    }
    return total;
}

var offSeatPosition;
function orderOffTicket(informtion) {
    var showid=$("#showTitle")[0].getAttribute("value");
    $.post("/theater/addOffTicket",{
        showid:showid,
        position:toValue(offSeatPosition)+";"
    },function(message){
        alert(message)
        if (message==="购票成功"){
            window.location.href="/theater/myAllShow";
        }
    })
}
function orderUserTicket() {
    var showid=$("#showTitle")[0].getAttribute("value");
    var position="";
    var root=$("#seatInformtion")[0];
    var chidren=root.childNodes;
    for (var i=0;i<chidren.length;i++){
        position=position+toValue(chidren.item(i).innerHTML)+";"
    }

    var number=chidren.length;
    var price=getPrice();

    var email=$("#email").val();
    var pass=$("#pass").val();

    $.post("/theater/addOrderOffTicket",{
        showid:showid,
        position:position,
        number:number,
        price:price,
        email:email,
        pass:pass
    },function(message){
        alert(message)
        if (message==="购买成功"){
            window.location.href="/theater/myAllShow";
        }
    })
}

function orderTicket() {
    var showid=$("#showTitle")[0].getAttribute("value");
    var position="";
    var root=$("#seatInformtion")[0];
    var chidren=root.childNodes;
    for (var i=0;i<chidren.length;i++){
        position=position+toValue(chidren.item(i).innerHTML)+";"
    }

    var number=chidren.length;
    var price=getPrice();

    $.post("/user/orderTicket",{
        showid:showid,
        position:position,
        number:number,
        price:price
    },function(message){
        alert(message)
        if (message==="购买成功"){
            window.location.href="/user/getAllTicket";
        }
    })
}

function getSeat() {

    var id=$("#showTitle")[0].getAttribute("value");

    $.post("/user/getShowSeat", {
        showid:id
    },function(message){
        message=JSON.parse(message);
        var row=message['row'];
        var col=message['col'];
        var seatList=message['rooms'];
        var root=$("#seatInfo")[0];

        while(root.firstChild) {
            root.removeChild(root.firstChild);
        }

        var title=document.createElement("div");
        title.innerHTML="<div>\n" +
            "            <button id=\"primaryChoose\" class=\"btn signButton\"  style=\"background-color: gray\"></button>Primary seats\n" +
            "            <button id=\"intermediateChoose\" class=\"btn signButton\" style=\"background-color: coral\"></button>Intermediate seats\n" +
            "            <button id=\"advancedChoose\" class=\"btn signButton\" style=\"background-color: indianred\"></button>Advanced seats\n" +
            "        </div>";
        root.appendChild(title);

        for (var i=0;i<row;i++){
            var line=document.createElement("div");
            for (var j=0;j<col;j++){
                var room=seatList[i*col+j];
                var temp=document.createElement("button");
                temp.className="btn seatButton";
                temp.value=(i+1)+","+(j+1);
                temp.onclick=function (event) {
                    var src=event.srcElement;
                    if (src.style.border==='2px solid rgb(76, 175, 80)'){
                        src.style.border='';
                        deleteSeat(toInformation(src.value));
                    }else{
                        var isChange=addSeat(toInformation(src.value));
                        if (isChange){
                            src.style.border='2px solid rgb(76, 175, 80)';
                        }
                    }
                };
                temp.style.backgroundColor=getColor(room.seatType);
                temp.innerHTML=room.empty===true?"":"人";
                if (!room.empty){
                    console.log(room,i,j);
                }
                temp.disabled=!room.empty||room.seatType===0;

                line.appendChild(temp);
            }
            root.appendChild(line);
        }
    } );

}

function getSeatOff() {
    var id=$("#showTitle")[0].getAttribute("value");

    $.post("/user/getShowSeat", {
        showid:id
    },function(message){
        message=JSON.parse(message);
        var row=message['row'];
        var col=message['col'];
        var seatList=message['rooms'];
        var root=$("#seatInfoOff")[0];

        while(root.firstChild) {
            root.removeChild(root.firstChild);
        }

        var title=document.createElement("div");
        title.innerHTML="<div>\n" +
            "            <button id=\"primaryChoose\" class=\"btn signButton\"  style=\"background-color: gray\"></button>Primary seats\n" +
            "            <button id=\"intermediateChoose\" class=\"btn signButton\" style=\"background-color: coral\"></button>Intermediate seats\n" +
            "            <button id=\"advancedChoose\" class=\"btn signButton\" style=\"background-color: indianred\"></button>Advanced seats\n" +
            "        </div>";
        root.appendChild(title);

        for (var i=0;i<row;i++){
            var line=document.createElement("div");
            for (var j=0;j<col;j++){
                var room=seatList[i*col+j];
                var temp=document.createElement("button");
                temp.className="btn seatButton";
                temp.value=(i+1)+","+(j+1);
                temp.onclick=function (event) {
                    var src=event.srcElement;
                   if (src.style.border==='2px solid rgb(76, 175, 80)'){
                       src.style.border='';
                       offSeatPosition="";
                   }else{
                       offSeatPosition=toInformation(src.value);
                       src.style.border='2px solid rgb(76, 175, 80)';
                   }
                };
                temp.style.backgroundColor=getColor(room.seatType);
                temp.innerHTML=room.empty===true?"":"人";
                if (!room.empty){
                    console.log(room,i,j);
                }
                temp.disabled=!room.empty||room.seatType===0;

                line.appendChild(temp);
            }
            root.appendChild(line);
        }
    } );

}