function logout() {
    $.post("/logout", function() {
        window.location.replace("/");
    });
}

function updateUser() {
    let formData = {
        'handle': $('#FormInputGroupHandle').val(),
        'profilePicture': $('#FormInputGroupProfilePicture').val(),
        'location': $('#FormInputGroupLocation').val(),
        'bio': $('#FormInputGroupBio').val()
    };

    $.ajax({
        type: 'POST',
        url: '/api/user/update',
        data: formData,
        dataType: 'json',
        encode: true
    }).done(setTimeout(function(data) {
        console.log(data);
        event.preventDefault();
        window.location.reload();
    }), 1000);

    event.preventDefault();
    window.location.reload();
}

$(document).ready(function() {
    $.ajax("/authenticate", {
        type: "GET",
    }).done(function(data) {
        console.log(data);
    })
});

$.ajaxSetup({
    beforeSend : function(xhr, settings) {
        if (settings.type === 'POST' || settings.type === 'PUT'
            || settings.type === 'DELETE' || settings.type === 'GET') {
            if (!(/^http:.*/.test(settings.url) || /^https:.*/
                .test(settings.url))) {
                xhr.setRequestHeader("X-XSRF-TOKEN",
                    Cookies.get('XSRF-TOKEN'));
            }
        }
    }
});