function doAjaxAuthSign(dir){
     $.ajax({
        type: "POST",
        url: '/authSignature',
        data: {"direccion": dir},
        dataType: 'json',
        success: function (res) {
            window.location.href =res.dir;
           console.log(res.dir);            
        },
        error: function (err) {
            console.error(err);
        }
    });
    
}