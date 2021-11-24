function PasswordVerify() {
    if(document.getElementById('pass1').value == document.getElementById('pass2').value){
        $('#mensagemLabel').text('Matching Passwords')
            .css('color', 'green');
        $('#btSave').prop('disabled', '');
    } else {

        $('#mensagemLabel').text('Not Matching Passwords')
            .css('color', 'red');
        $('#btSave').prop('disabled', 'true');
    }
}

function SecondPassword(){

    $('#passRepeatLabel').css('display', 'block');

    $('#pass2').css('display', 'block')
        .prop('required', 'true');

    $('#pass1').val('');

    $('#mensagemLabel').css('display', 'block');
}