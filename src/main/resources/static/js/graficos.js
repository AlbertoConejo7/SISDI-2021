var departamentos=[{
                        name: 'Hacienda Municipal',
                        y: 10
                    }, {
                        name: 'Alcaldia',
                        y: 50
                    }, {
                        name: 'Servicios Públicos',
                        y: 25
                    }, {
                        name: 'Proveeduría',
                        y: 90
                    }, {
                        name: 'Concejo Municipal',
                        y: 75
                    }, {
                        name: 'Recursos Humanos',
                        y: 78
                    }];
function getData(){
    console.log("Antes "+departamentos[0]);
    var name = "data";
    $.ajax({
        type: "POST",
        url: '/getDataFiles',
        data: {"name": name},
        dataType: 'json',
        success: function (res) {
            console.log(res[0].Dep);
           departamentos=res[0].Dep;
           console.log(departamentos);
           generateGraphicDep();
        },
        error: function (err) {
            console.error(err);
        }
    });
}
// Grafico Departamentos
function generateGraphicDep() {
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

$(function ($) {
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
            pointFormat: '{series.name}: <b>{point.percentage:.1f}%</b>'
        },
        accessibility: {
            point: {
                valueSuffix: '%'
            }
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
                data: []
            }]
    });
});
