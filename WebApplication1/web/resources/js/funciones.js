/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


$(document).ready(function () {
    $('#bsq_avanzada').hide();
    sameSizeLi();
    estilosReloj();
})


function ocultar_bsq_av() {
    console.log("ocultar_bq_av");
    if ($('#inlineCheckbox1').is(":checked")) {
        $('#bsq_avanzada').show();
    } else {
        $('#bsq_avanzada').hide();
    }
}


function isCheckedById(id) {
    alert(id);
    var checked = $("input[@id=" + id + "]:checked").length;
    alert(checked);

    if (checked == 0) {
        return false;
    } else {
        return true;
    }
}

function sameSizeLi() {
    var lis = $(".sameSize").find("li");
    lis.each(function (index, valor) {
        if (index == 0) {
            $(valor).css({
                "margin-left": "2px"
            })
        }

        $(valor).css({
            "width": "40px",
            "heigth": "40px",
            "border": "1px solid #ccc",
            "border-radius": "5px"
        })
        if (index == (lis.length - 1)) {
            $(valor).css({
                "width": "60px"
            })
        }
    });
}
function validarSiNumero(teldonante) {
    if (!/^([0-9])*$/.test(teldonante))
        alert("El valor " + teldonante + " no es un número");
}


function soloNumeros(e) {
    key = e.keyCode || e.which;
    tecla = String.fromCharCode(key).toLowerCase();
    letras = " 0123456789";
    especiales = "46-8-9-27-13-110-190";

    tecla_especial = false
    for (var i in especiales) {
        if (key == especiales[i]) {
            tecla_especial = true;
            break;
        }
    }

    if (letras.indexOf(tecla) == -1 && !tecla_especial) {
        return false;
    }
}


function soloLetras(e) {
    key = e.keyCode || e.which;
    tecla = String.fromCharCode(key).toLowerCase();
    letras = " áéíóúabcdefghijklmnñopqrstuvwxyz";
    especiales = "8-37-39-46";

    tecla_especial = false
    for (var i in especiales) {
        if (key == especiales[i]) {
            tecla_especial = true;
            break;
        }
    }

    if (letras.indexOf(tecla) == -1 && !tecla_especial) {
        return false;
    }
}


function estilosReloj() {
    if ($('#reloj_sistema').length > 0) {
        $('#reloj_sistema').removeClass("ui-clock");
        $('#reloj_sistema').removeClass("ui-widget");
        $('#reloj_sistema').removeClass("ui-widget-header");
        $('#reloj_sistema').removeClass("ui-corner-all");

    }
}

function foco(opcion) {
    switch (opcion) {
        case 1:
            $('#tituloRevista').focus();
            break;
    }
}


function ocultar_buqueda() {
    console.log("ocultar_buqueda");
    $('#contenedor_busquedas input').each(function () {
        $(this).prop("disabled", true);
    });
    $('#contenedor_busquedas button ').each(function () {
        $(this).prop("disabled", true);
    });
}

function ocultar_buqueda_2() {
    $('#contenedor_busquedas').hide();
}

function redireccionar(url){
  console.log("redirigiendo...");
  window.location=""+url;
} 

