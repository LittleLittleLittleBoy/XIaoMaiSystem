function showQRCode(message){
    document.getElementById("qrcode").innerHTML='';
    new QRCode(document.getElementById("qrcode"), message);
}
function payMoney(){
    var ticketid=$("#ticketid").text();
    var pass=$("#password").val();
    var useIntegral=$("#useIntegral")[0].checked;

    $.post("/user/pay",{
        "ticketid":ticketid,
        "pass":pass,
        "useIntegral":useIntegral
    },function (message) {
        if (message==="购票成功"){
            window.location.href="/user/getAllTicket";
        }
        alert(message);
    })
}

