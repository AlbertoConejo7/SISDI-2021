function getData(){
    var name = "data";
    $.ajax({
        type: "POST",
        url: '/getDataFiles',
        data: {"name": name},
        dataType: 'json',
        success: function (res) {
            console.log(res);
           var departamentos=res[0].Dep;
           var years=res[0].Years;
           generateGraphicDep(departamentos);
           generateGraphicYears(years);
           $("#transferC").text(res[2].Trasladados);
           $("#prestadosC").text(res[2].Prestados);
           $("#actasC").text(res[2].Eliminados);
           
        },
        error: function (err) {
            console.error(err);
        }
    });
}
// Grafico Departamentos
function generateGraphicDep(departamentos) {
    $('#departamentos').highcharts({
        chart: {
            plotBackgroundColor: null,
            plotBorderWidth: null,
            plotShadow: false,
            type: 'pie'
        },
        title: {
            text: 'Expedientes por Departamento'
        },
        tooltip: {
            pointFormat: '{series.name}: <b>{point.y}</b>'
        },

        plotOptions: {
            pie: {
                allowPointSelect: true,
                cursor: 'pointer',
                dataLabels: {
                    enabled: false
                },
                showInLegend: true
            }
        },
        series: [{
                name: 'Expedientes',
                colorByPoint: true,
                data: departamentos
            }]
    });
}

function generateGraphicYears(years)  {
    $('#annio').highcharts({
        chart: {
            plotBackgroundColor: null,
            plotBorderWidth: null,
            plotShadow: false,
            type: 'pie'
        },
        title: {
            text: 'Expedientes por Año'
        },
        tooltip: {
            pointFormat: '{series.name}: <b>{point.y}</b>'
        },
        plotOptions: {
            pie: {
                allowPointSelect: true,
                cursor: 'pointer',
                dataLabels: {
                    enabled: false
                },
                showInLegend: true
            }
        },
        series: [{
                name: 'Expedientes',
                colorByPoint: true,
                data: years
            }]
    });
};
