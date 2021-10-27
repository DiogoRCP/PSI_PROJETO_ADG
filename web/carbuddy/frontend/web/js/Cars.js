//SearchCar('wBA1V910805G93301', 2016);

function SearchCar(vin) {
    $.ajax({
        url: `https://vpic.nhtsa.dot.gov/api/vehicles/decodevin/${vin}`,
        type: "GET",
        data: {'format': 'json'},
        dataType: "json"
    }).done(function (resposta) {
        console.log(Object.entries(resposta)[3][1]);
        $('#cars-brand').val(Object.entries(resposta)[3][1][6]['Value'])
            .prop('readonly', true);
        $('#cars-model').val(Object.entries(resposta)[3][1][8]['Value'])
            .prop('readonly', true);
        $('#cars-cartype').val(Object.entries(resposta)[3][1][13]['Value'])
            .prop('readonly', true);
        $('#cars-displacement').val(Object.entries(resposta)[3][1][69]['Value'])
            .prop('readonly', true);
        $('#cars-fueltype').val(Object.entries(resposta)[3][1][75]['Value'])
            .prop('readonly', true);
    }).fail(function (jqXHR, textStatus) {
        //console.log("Request failed: " + textStatus);

    }).always(function () {
        //console.log("completou");
    });
}