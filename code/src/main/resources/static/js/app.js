var logout = function() {
    disconnect();
    $.post("/logout", function() {
        window.location.replace("/");
    });
    return true;
};

var updateUser = function() {
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
    }).done(function() {
        location.reload();
    });

    event.preventDefault();
    location.reload();
};

$(document).ready(function() {
    $.ajax("/authenticate", {
        type: "GET",
        statusCode: {
            200: function () {
                connect();
            },
            403: function () {
                console.log("403 on /authenticate")
            }
        }
    });
});

$.ajaxSetup({
    beforeSend : function(xhr, settings) {
        if (settings.type == 'POST' || settings.type == 'PUT'
            || settings.type == 'DELETE' || settings.type == 'GET') {
            if (!(/^http:.*/.test(settings.url) || /^https:.*/
                .test(settings.url))) {
                // Only send the token to relative URLs i.e. locally.
                xhr.setRequestHeader("X-XSRF-TOKEN",
                    Cookies.get('XSRF-TOKEN'));
            }
        }

    }
});