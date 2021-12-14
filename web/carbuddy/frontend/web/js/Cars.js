SearchCar = (vin) => {
    if(vin.length == 17) {
        $.ajax({
            url: `https://vpic.nhtsa.dot.gov/api/vehicles/decodevin/${vin}`,
            type: "GET",
            data: {'format': 'json'},
            dataType: "json"
        }).done(function (resposta) {
            $('#cars-brand').val(Object.entries(resposta)[3][1][6]['Value'])
                //.prop('readonly', (Object.entries(resposta)[3][1][6]['Value'] != null) ? true : false);

            $('#cars-model').val(Object.entries(resposta)[3][1][8]['Value'])
                //.prop('readonly', (Object.entries(resposta)[3][1][8]['Value'] != null) ? true : false);

            $('#cars-modelyear').val(Object.entries(resposta)[3][1][9]['Value'])
                //.prop('readonly', (Object.entries(resposta)[3][1][9]['Value'] != null) ? true : false);

            $('#cars-cartype').val((Object.entries(resposta)[3][1][13]['Value']=="TRUCK ")?"TRUCK":Object.entries(resposta)[3][1][13]['Value']);
            //.prop('disabled', (Object.entries(resposta)[3][1][13]['Value'] != null)?true:false);

            $('#cars-displacement').val(Object.entries(resposta)[3][1][69]['Value'])
                //.prop('readonly', (Object.entries(resposta)[3][1][69]['Value'] != null) ? true : false);

            $('#cars-fueltype').val(Object.entries(resposta)[3][1][75]['Value']);
            //.prop('disabled', (Object.entries(resposta)[3][1][75]['Value'] != null)?true:false);
        }).fail(function (jqXHR, textStatus) {
            //console.log("Request failed: " + textStatus);

        }).always(function () {
            //console.log("completou");
        });
    }
}