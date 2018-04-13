function addNewActivity() {
    var title=$('#title').val();
    var roomid=$('#roomid').val();
    var time=$('#time').val();
    var price1=$('#price1').val();
    var price2=$('#price2').val();
    var price3=$('#price3').val();
    var type=$('#type').val();
    var description=$('#description').val();

    if (title===""||title===undefined||
        roomid===""||roomid===undefined||
        time===""||time===undefined||
        price1===""||price1===undefined||
        price2===""||price2===undefined||
        price3===""||price3===undefined||
        type===""||type===undefined||
        description===""||description===undefined){
        alert("信息有误");
    }else{
        $.post("/theater/addNewShowInfo", { title: title,
            roomid: roomid,
            time:time,
            price1:price1,
            price2:price2,
            price3:price3,
            type:type,
            description:description
        },function(event){
            if (event!=="true"){
                alert(event)
            }else{
                window.location.href='/theater/myAllShow'
            }
        } );
    }
}