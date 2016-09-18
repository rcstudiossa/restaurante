package br.com.restaurante.model;

import java.io.Serializable;

import br.com.restaurante.util.Constantes;
import br.com.topsys.util.TSUtil;

@SuppressWarnings("serial")
public class TipoRespostaModel implements Serializable {

	private Long id;
	
	private String descricao;

	public Long getId() {
		return TSUtil.tratarLong(id);
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	
	public boolean isCombo() {
		return Constantes.TIPO_RESPOSTA_QUIZ_COMBO.equals(this.getId());
	}

	public boolean isRadio() {
		return Constantes.TIPO_RESPOSTA_QUIZ_RADIO.equals(this.getId());
	}
	
	public boolean isBooleano() {
		return Constantes.TIPO_RESPOSTA_QUIZ_BOOLEANO.equals(this.getId());
	}

	public boolean isMultiplo() {
		return Constantes.TIPO_RESPOSTA_QUIZ_MULTIPLO.equals(this.getId());
	}

	public boolean isNumerico() {
		return Constantes.TIPO_RESPOSTA_QUIZ_NUMERICO.equals(this.getId());
	}
	
	public boolean isPontoFlutuante() {
		return Constantes.TIPO_RESPOSTA_QUIZ_PONTO_FLUTUANTE.equals(this.getId());
	}

	public boolean isData() {
		return Constantes.TIPO_RESPOSTA_QUIZ_DATA.equals(this.getId());
	}
	
	public boolean isDataHora() {
		return Constantes.TIPO_RESPOSTA_QUIZ_DATA_HORA.equals(this.getId());
	}

	public boolean isTexto() {
		return Constantes.TIPO_RESPOSTA_QUIZ_TEXTO_AREA.equals(this.getId());
	}

	public boolean isInputTexto() {
		return Constantes.TIPO_RESPOSTA_QUIZ_TEXTO_INPUT.equals(this.getId());
	}

	public boolean isHora() {
		return Constantes.TIPO_RESPOSTA_QUIZ_HORA.equals(this.getId());
	}
	
	public boolean isConsequencia() {
		return Constantes.TIPO_RESPOSTA_CONSEQUENCIA.equals(this.getId());
	}
	
	public boolean isInformativa() {
		return Constantes.TIPO_RESPOSTA_INFORMATIVA.equals(this.getId());
	}
	
	public boolean isVazia() {
		return Constantes.TIPO_RESPOSTA_VAZIA.equals(this.getId());
	}

	public boolean isInput() {
		return this.isTexto() || this.isInputTexto() || this.isNumerico() || this.isPontoFlutuante() || this.isInputMask();
	}

	public boolean isInputMask() {
		return Constantes.TIPO_RESPOSTA_QUIZ_INPUTMASK.equals(this.getId());
	}
	
	public boolean isMultiploPanel() {
		return Constantes.TIPO_RESPOSTA_MULTIPLO_PANEL.equals(this.getId());
	}
	
	public boolean isListaInputText() {
		return Constantes.TIPO_RESPOSTA_LISTA_INPUT_TEXT.equals(this.getId());
	}
	
	public boolean isListaInputTextArea() {
		return Constantes.TIPO_RESPOSTA_LISTA_INPUT_TEXTAREA.equals(this.getId());
	}

	public boolean isComboSQL() {
		return Constantes.TIPO_RESPOSTA_COMBO_SQL.equals(this.getId());
	}
	
	public boolean isKeyFilter() {
		return Constantes.TIPO_RESPOSTA_KEY_FILTER.equals(this.getId());
	}
	
	public boolean isJqueryMask() {
		return Constantes.TIPO_RESPOSTA_JQUERY_MASK.equals(this.getId());
	}

}
