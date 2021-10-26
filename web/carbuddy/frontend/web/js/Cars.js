//SearchCar('wBA1V910805G93301', 2016);

function SearchCar(vin, year) {
    $.ajax({
        url: `https://vpic.nhtsa.dot.gov/api/vehicles/decodevin/${vin}`,
        type: "GET",
        data: {'modelyear': year, 'format':'json'},
        dataType: "json"
    }).done(function (resposta) {
        console.log(resposta);
    }).fail(function (jqXHR, textStatus) {
        console.log("Request failed: " + textStatus);

    }).always(function () {
        console.log("completou");
    });
}