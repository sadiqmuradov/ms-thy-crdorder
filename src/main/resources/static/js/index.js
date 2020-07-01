$(function () {
    $(document).on("change", "input[name=tkNo]", function () {
        if ($(this).val() === '1') {
            $("#tk_yes").hide();
            $("#tk_no").show();
        } else {
            $("#tk_yes").show();
            $("#tk_no").hide();
        }
        $("#message").html("");
        $("#existingUserData").hide();
    });
    $("#country").on("change", function (e) {
        $("#loader").show();
        var countryCode = $(this).val();
        $.getJSON("getCity/" + countryCode, function (data) {
            $("#city option").remove();
            $("#city").append("<option value='0'>Seçin</option>");
            $.each(data, function (key, val) {
                $("#city").append("<option value='" + val.code + "'>" + val.name + "</option>");
            });
            $("#loader").hide();
        });
    });
    $("#check_tkno_form").on("click", function (e) {
        $("#loader").show();
        $("#existingUserData").hide();
        $("#message").html("");
        e.preventDefault();
        var tkNo = $("#checkTkNo").val();
        $.ajax({
            url: 'checkTKui/' + tkNo,
            success: function (result) {
                var jsonobj = JSON.parse(result);
                if (jsonobj.code === 'OK') {
                    if (jsonobj.code === 'OK') {
                        var params = jsonobj.data.split(",");
                        var name = params[0];
                        var surname = params[1];
                        name = name.substr(name.indexOf("name=") + 5);
                        surname = surname.substr(surname.indexOf("surname=") + 8);
                        $("#existingUserData").show();
                        $("#existingname").html(name);
                        $("#existingsurname").html(surname);
                        $("#loader").hide();
                    } else {
                        $("#existingUserData").html("<b style='color: red'>" + jsonobj.message + "</b>");
                        $("#loader").hide();
                    }
                } else {
                    $("#message").html("<b style='color: red'>" + jsonobj.message + "</b>");
                    $("#loader").hide();
                }
            },
            error: function (result) {
                $("#message").html("<b style='color: red'>Yanlışlıq baş verdi. Yenidən cəhd edin.</b>");
                $("#loader").hide();
            },
        });
    });
    $("#submit_tkno_form").on("click", function (e) {
        $("#message").html("");
        e.preventDefault();
        $("#existingUserData").hide();
        $("#loader").show();
        var form = $("#tk_no_form");
        var allIsOk = true;
        $(document).find("select").each(function () {
            if ($(this).val() === '0') {
                allIsOk = false;
            }
        });
        var data = form.serialize();
        if (allIsOk === true) {
            $.ajax({
                type: "POST",
                url: 'registerCustomerInTHYui',
                data: data,
                success: function (result) {
                    var jsonobj = JSON.parse(result);
                    if (jsonobj.code === 'OK') {
                        $("#message").html("<b style='color: green'> TK NÖMRƏSİ: " + jsonobj.tk +
                            " <br/> Qeydiyyat tamamlandı. Yeni istifadəçi yaradın.</b>");
                        cleanInputs(form);
                        $("#loader").hide();
                    } else {
                        $("#message").html("<b style='color: red'>" + jsonobj.message + "</b>");
                        $("#loader").hide();
                    }
                },
                error: function (result) {
                    $("#message").html("<b style='color: red'>Yanlışlıq baş verdi. Yenidən cəhd edin.</b>");
                    $("#loader").hide();
                },
            });
        } else {
            $("#message").html("<b style='color: red'>Tələb edilən xanaların hamsını doldurun.</b>");
            $("#loader").hide();
        }
    });

    function cleanInputs(section) {
        section.find("input[type=text],input[type=number],input[type=date],input[type=email]").each(function () {
            $(this).val("");
        });
        section.find("input[type=radio]").prop('checked', false);
        section.find("select").prop('selectedIndex', 0);
    }
});