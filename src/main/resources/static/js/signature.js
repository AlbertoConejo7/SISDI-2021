function formatPEM(raw) {
    var pemString = pvtsutils.Convert.ToBase64(raw);
    var stringLength = pemString.length;
    var resultString = "";

    for (var i = 0, count = 0; i < stringLength; i++, count++) {
        if (count > 63) {
            resultString = resultString + '\n';
            count = 0;
        }

        resultString = resultString + pemString[i];
    }

    return resultString;
}

async function continueHandler(event) {
    try {
        var demoData = pvtsutils.Convert.FromUtf8String("Test message");
        var provider = await event.detail.server.getCrypto(event.detail.providerId);
        console.log(event.detail.certificateId);
        provider.sign = provider.subtle.sign.bind(provider.subtle);

        pkijs.setEngine(
                "newEngine",
                provider,
                new pkijs.CryptoEngine({
                    name: "",
                    crypto: provider,
                    subtle: provider.subtle,
                })
                );

        var cert = await provider.certStorage.getItem(event.detail.certificateId);
        var privateKey = await provider.keyStorage.getItem(event.detail.privateKeyId);
        var certRawData = await provider.certStorage.exportCert('raw', cert);

        var pkiCert = new pkijs.Certificate({
            schema: asn1js.fromBER(certRawData).result,
        });

        var signedData = new pkijs.SignedData({
            version: 1,
            encapContentInfo: new pkijs.EncapsulatedContentInfo({
                eContentType: "1.2.840.113549.1.7.1", // "data" content type
            }),
            signerInfos: [
                new pkijs.SignerInfo({
                    version: 1,
                    sid: new pkijs.IssuerAndSerialNumber({
                        issuer: pkiCert.issuer,
                        serialNumber: pkiCert.serialNumber,
                    }),
                }),
            ],
            certificates: [pkiCert],
        });

        var contentInfo = new pkijs.EncapsulatedContentInfo({
            eContent: new asn1js.OctetString({
                valueHex: demoData,
            }),
        });

        signedData.encapContentInfo.eContent = contentInfo.eContent;

        await signedData.sign(privateKey, 0, "sha-256");

        var cms = new pkijs.ContentInfo({
            contentType: "1.2.840.113549.1.7.2",
            content: signedData.toSchema(true),
        });
        var result = cms.toSchema().toBER(false);
        var pem = formatPEM(result);
        //console.log(pem);
        signatureLogin(pem);
    } catch (error) {
        createAlertNoSignatureCMS();
        console.error(error);
    }
}
var el = document.createElement('peculiar-fortify-certificates');
// el.language = 'ru';
el.debug = true;
el.filters = {
    // onlySmartcards: true,
    expired: false,
    onlyWithPrivateKey: true,
    //   subjectDNMatch: 'apple',
    //   subjectDNMatch: new RegExp(/apple/),
    //   issuerDNMatch: 'demo',
    //   issuerDNMatch: new RegExp(/demo/),
    //   providerNameMatch: 'MacOS',
    //   providerNameMatch: new RegExp(/MacOS/),
    //   keyUsage: ['digitalSignature'],
    //   certificateIdMatch: 'test',
};

el.addEventListener('cancel', function () {
    history.back();
});

el.addEventListener('continue', continueHandler);

document.getElementsByTagName('section')[0].prepend(el);

function signatureLogin(certificateId) {
    var name = certificateId;
    //console.log("Cert: "+name);
    $.ajax({
        type: "POST",
        url: '/signatureVerification',
        data: {"name": name},
        dataType: 'json',
        success: function (res) {
            if(res.Encontrado==true){
                nextPage(name);
                console.log(res.Encontrado);
            }else{
                createAlertNoSignatureBD();
            }
            
        },
        error: function (err) {
            console.error(err);
        }
    });
}

function nextPage(certificateId) { 
    var name = certificateId;
    //console.log("Cert: "+name);
    $.ajax({
        type: "POST",
        url: '/saveSignatureAuth',
        data: {"name": name},
        dataType: 'json',
        success: function (res) {
            console.log(res);
            window.location.href = res.dir;
        },
        error: function (err) {
            console.error(err);
        }
    });
}

function createAlertNoSignatureBD() {
    var alertClasses = ["alert"," alert-dismissible"," alert-danger", " fade","show"];
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
        html: "El certificado no se encuentra en el Sistema."
    }).appendTo(msg);
    $("<strong />", {
        html: "Por favor verificar que esta seleccionando el certificado correcto "
    }).appendTo(msg);
    $('#signNotice').append(msg);
}

function createAlertNoSignatureCMS() {
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
        html: "El ingreso al Certificado fallo"
    }).appendTo(msg);
    $("<strong />", {
        html: "Por favor verificar que esta permitiendo el ingreso del certificado"
    }).appendTo(msg);
    $('#signNotice').append(msg);
}
