function destributeTicket() {
    var showid=$("#showTitle")[0].getAttribute("value");

    var price1=$("#price1")[0].innerHTML;
    var price2=$("#price2")[0].innerHTML;
    var price3=$("#price1")[0].innerHTML;

    var number1=$("#primaryInput").val();
    var number2=$("#intermediateInput").val();
    var number3=$("#advancedInput").val();

    if (parseInt(number1)+parseInt(number2)+parseInt(number3)>20){
        $("#orderInformation")[0].innerHTML="预约票数不能超过20张";
        return;
    }

    $.post("/user/destribute",{
        showid:showid,
        number1:number1,
        number2:number2,
        number3:number3

    },function (message) {
        alert(message);
    })

}