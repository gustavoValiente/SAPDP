function maiusculas(v){
	 v=v.toUpperCase();             //Maiúsculas
     return v;
}	
function mascara(o,f){
	v_obj=o;
	v_fun=f;
	setTimeout("execmascara()",1);
}

function execmascara(){
	v_obj.value=v_fun(v_obj.value);
}
function sodigito(v){
	v=v.replace(/\D/g,"");                 //Remove tudo o que não é dígito
    return v;
}	
function incremento(valor){
	valor = valor +1;
}

//Tradução do componente p:calendar
PrimeFaces.locales['pt_BR'] = {
	    closeText : 'Fechar',
	    prevText : 'Anterior',
	    nextText : 'Proximo',
	    currentText : 'Hoje',
	    monthNames : [ 'Janeiro', 'Fevereiro', 'Marco', 'Abril', 'Maio', 'Junho','Julho', 'Agosto', 'Setembro', 'Outubro', 'Novembro', 'Dezembro' ],
	    monthNamesShort : [ 'Jan', 'Fev', 'Mar', 'Abr', 'Mai', 'Jun', 'Jul', 'Ago','Set', 'Out', 'Nov', 'Dez' ],
	    dayNames : [ 'Domingo', 'Segunda', 'Terca', 'Quarta', 'Quinta', 'Sexta','Sabado' ],
	    dayNamesShort : [ 'Dom', 'Seg', 'Ter', 'Qua', 'Qui', 'Sex', 'Sab' ],
	    dayNamesMin : [ 'D', 'S', 'T', 'Q', 'Q', 'S', 'S' ],
	    weekHeader : 'Semana',
	    firstDay : 1,
	    isRTL : false,
	    showMonthAfterYear : false,
	    yearSuffix : '',
	    timeOnlyTitle : 'So Horas',
	    timeText : 'Tempo',
	    hourText : 'Hora',
	    minuteText : 'Minuto',
	    secondText : 'Segundo',
	    ampm : false,
	    month : 'Mes',
	    week : 'Semana',
	    day : 'Dia',
	    allDayText : 'Todo Dia'
	};


/*<![CDATA[*/
$(document)
.unbind('keydown')
.bind(
	'keydown',
	function(event) {
		var doPrevent = false;
		if (event.keyCode === 8) {
			var d = event.srcElement || event.target;
			if ((d.tagName.toUpperCase() === 'INPUT' && (d.type
					.toUpperCase() === 'TEXT'
					|| d.type.toUpperCase() === 'PASSWORD' || d.type
					.toUpperCase() === 'FILE'))
					|| d.tagName.toUpperCase() === 'TEXTAREA') {
				doPrevent = d.readOnly || d.disabled;
			} else {
				doPrevent = true;
			}
		}

		if (doPrevent) {
			event.preventDefault();
		}
	}); /*]]>*/
