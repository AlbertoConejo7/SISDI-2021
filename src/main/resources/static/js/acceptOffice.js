function saveResponseTime() {
    var limite = document.getElementById("recepcionTimeValue");
    var dateLimit=document.getElementById("datelimit");
    

     $("#responseTime .close").click();
        showAlert();
         
    
}
function mostrarResultados(datos) {
      console.log(datos);
}
function showAlert() {
    var alertSave=$("#alert-save");
    alertSave.addClass("show");
    alertSave.removeClass("fade");
    window.setTimeout(function () {
       alertSave.addClass("fade");
       alertSave.removeClass("show");
    }, 3000);
};

function getJSON2(url, data, callback) {
     fetch(url, {
        method: 'POST',
        body: data
    }).then((result) => {
        return result.json();
    }).then(callback);
}