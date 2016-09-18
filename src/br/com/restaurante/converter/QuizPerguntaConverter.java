/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package br.com.restaurante.converter;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;

import br.com.restaurante.business.QuizPerguntaBS;
import br.com.restaurante.model.QuizPerguntaModel;
import br.com.topsys.util.TSUtil;

@ManagedBean
public class QuizPerguntaConverter implements Converter {

	@EJB
	private QuizPerguntaBS quizPerguntaBS;

	@Override
	public Object getAsObject(FacesContext fc, UIComponent uic, String query) {

		QuizPerguntaModel model = null;

		if (!TSUtil.isEmpty(query) && TSUtil.isNumeric(query)) {

			model = this.quizPerguntaBS.obterCrudModel(new QuizPerguntaModel(Long.valueOf(query)));

		}

		if (TSUtil.isEmpty(model)) {

			model = new QuizPerguntaModel();

		}

		return model;
	}

	@Override
	public String getAsString(FacesContext fc, UIComponent uic, Object o) {
		QuizPerguntaModel model = (QuizPerguntaModel) o;
		return String.valueOf(model.getId());
	}

}
