var mascaraInicial = '';

function maskDate(w, e, m, r, a) {
	var mascara = m.replace(/\w/g, '#');
	maskIt(w, e, mascara, r, a);
	w.style.backgroundColor = '';
	if (w.value.length == 10) {
		if (!isValidDate(w)) {
			w.style.backgroundColor = '#efcccc';
		}
	}
}

function maskIt(w, e, m, r, a) {
	if (!e)
		var e = window.event
	if (e.keyCode)
		code = e.keyCode;
	else if (e.which)
		code = e.which;

	var txt = (!r) ? w.value.replace(/[^\d]+/gi, '') : w.value.replace(
			/[^\d]+/gi, '').reverse();
	var mask = (!r) ? m : m.reverse();
	var pre = (a) ? a.pre : "";
	var pos = (a) ? a.pos : "";
	var ret = "";

	if (code == 9 || code == 8
			|| txt.length == mask.replace(/[^#]+/g, '').length)
		return false;

	for (var x = 0, y = 0, z = mask.length; x < z && y < txt.length;) {
		if (mask.charAt(x) != '#') {
			ret += mask.charAt(x);
			x++;
		} else {
			ret += txt.charAt(y);
			y++;
			x++;
		}
	}

	ret = (!r) ? ret : ret.reverse();
	w.value = pre + ret + pos;
}

function validateDate(date) {
	date.style.backgroundColor = '';
	if (date.value.length < 10) {
		if (date.value.length > 0) {
			date.style.backgroundColor = '#efcccc';
		}
		date.value = '';
	} else if (!isValidDate(date)) {
		date.value = mascaraInicial;
		date.style.backgroundColor = '#efcccc';
	}

}

function focusDate(date) {
	if (date.value.length < 10 || !isValidDate(date)) {
		date.style.backgroundColor = '';
		date.value = '';
	}
}

function blurDate(date) {
	if (date.value.length < 10 || !isValidDate(date)) {
		date.style.backgroundColor = '';
		date.value = mascaraInicial;
	}
}

function isValidDate(campo) {
	var date = campo.value;
	var ardt = new Array;
	var ExpReg = new RegExp(
			"(0[1-9]|[12][0-9]|3[01])/(0[1-9]|1[012])/[12][0-9]{3}");
	ardt = date.split("/");
	validado = true;
	if (date.search(ExpReg) == -1) {
		validado = false;
	} else {
		var dia = ardt[0];
		var mes = ardt[1];
		var ano = ardt[2];
		if (((mes == 4) || (mes == 6) || (mes == 9) || (mes == 11))
				&& (dia > 30))
			validado = false;
		else if (mes == 2) {
			if ((dia > 28) && ((ano % 4) != 0))
				validado = false;
			if ((dia > 29) && ((ano % 4) == 0))
				validado = false;
		}
	}

	return validado;
}

function clickClassEnter(e, btnClass) {

	var code = e.keyCode ? e.keyCode : e.which;

	if (code === 13) {
		$('.' + btnClass).click();
		return false;
	}

	return true;

}

function handleRequest(widgerVar, args) {

	if (args.valido) {
		PF(widgerVar).hide();
	}

}

function disableEnter(e) {

	var code = e.keyCode ? e.keyCode : e.which;

	if (code === 13) {
		return false;
	}

}