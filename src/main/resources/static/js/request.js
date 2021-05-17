function acceptRequest() {
    var id = $("#idRequest").val();
    console.log("Cert: "+id);
    $.ajax({
        type: "POST",
        url: '/approval',
        data: {"ID": id},
        dataType: 'json',
        success: function (res) {
            if(res.Aceptado==true){
                window.location.href = "/offices/listRequests";
                console.log("guardado");
            }else{
                console.log("no guardado");
                createAlertLoansError();
            }
            
        },
        error: function (err) {
            console.error(err);
        }
    });
}

function deniedRequest() {
    var id = $("#idRequest").val();
    console.log("Cert: "+id);
    $.ajax({
        type: "POST",
        url: '/rejected',
        data: {"ID": id},
        dataType: 'json',
        success: function (res) {
            if(res.Aceptado==true){
                window.location.href = "/offices/listRequests";
                console.log("guardado");
            }else{
                console.log("no guardado");
                createAlertLoansError();
            }
            
        },
        error: function (err) {
            console.error(err);
        }
    });
}

function createAlertLoansError() {
   var alertClasses = ["alert"," alert-dismissible"," alert-danger"];
    var msg = $("<div/>", {
        "class": alertClasses.join(" "),
        "role":"alert"
    });
    $("<span />", {
      "class": "close",
      "data-dismiss": "alert",
      html: "x"
    }).appendTo(msg);
    
    $("<h6 />", {
        html: "Error:"
    }).appendTo(msg);
    $("<strong />", {
        html: "Hubo un fallo, intente de nuevo"
    }).appendTo(msg);
    $('#loansNotification').append(msg);
}
