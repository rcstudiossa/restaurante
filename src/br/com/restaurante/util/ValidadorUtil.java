package br.com.restaurante.util;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.jexl2.Expression;
import org.apache.commons.jexl2.JexlContext;
import org.apache.commons.jexl2.JexlEngine;
import org.apache.commons.jexl2.MapContext;

import br.com.restaurante.dao.ExecucaoPLSQLDAO;
import br.com.restaurante.model.ValidadorModel;
import br.com.topsys.exception.TSApplicationException;
import br.com.topsys.util.TSUtil;
import br.com.topsys.web.util.TSFacesUtil;

@SuppressWarnings("serial")
public class ValidadorUtil implements Serializable {

	private ExecucaoPLSQLDAO execucaoPLSQLDAO;

	public ValidadorUtil() {
		this.execucaoPLSQLDAO = new ExecucaoPLSQLDAO();
	}

	public Map<String, String> tratarValidadores(String respostaDada, Map<String, Object> mapa, List<ValidadorModel> validadores, boolean flagSalvar) {

		Map<String, String> mensagens = new HashMap<String, String>();

		TSFacesUtil.removeObjectInSession(Constantes.SESSION_VALIDADOR);
		TSFacesUtil.addObjectInSession(Constantes.SESSION_VALIDADOR, mapa);

		for (ValidadorModel validadorModel : validadores) {

			if (!TSUtil.isEmpty(validadorModel.getExpressao())) {

				Object xxx = this.executarFormulaObject(validadorModel.getExpressao(), mapa);

				if (xxx != null && xxx.getClass() == Boolean.class && ((Boolean) xxx) == Boolean.TRUE) {

					if (flagSalvar || !validadorModel.getFlagValidacaoFinal()) {

						mensagens.put(validadorModel.getAviso(), validadorModel.getTipoAviso());
						
					}

				}

			} else {

				try {

					this.execucaoPLSQLDAO.executarPLSQL(validadorModel.getQueryValidacao(), Utilitario.getAtendimentoSessao().getId(), null, Utilitario.getUsuarioLogado(), respostaDada);

				} catch (TSApplicationException e) {

					mensagens.putAll(Utilitario.tratarErroExecucaoPLSQL(e.getMessage()));

				}

			}

		}
		
		return mensagens;

	}

	public Object executarFormulaObject(String formula, Map<String, Object> mapa) {

		JexlEngine jexl = new JexlEngine();
		jexl.setSilent(true);
		jexl.setLenient(true);

		Expression expression = jexl.createExpression(formula);

		Map<String, Object> functions = new HashMap<String, Object>();
		functions.put("math", Math.class);
		functions.put("double", Double.class);
		functions.put("integer", Integer.class);
		functions.put("string", String.class);
		jexl.setFunctions(functions);

		JexlContext jexlContext = new MapContext(mapa);

		return expression.evaluate(jexlContext);

	}

	public Double executarFormula(String formula, Double... campos) {

		HashMap<String, Object> mapa = new HashMap<String, Object>();

		int i = 1;

		for (Double campo : campos) {

			mapa.put("P" + i++, campo);

		}

		return this.executarFormula(formula, mapa);

	}

	public Double executarFormula(String formula, HashMap<String, Object> mapa) {
		return this.converterDouble(this.executarFormulaObject(formula, mapa));
	}
	
	public Double converterDouble(Object resultado) {
		
		if (resultado instanceof BigDecimal) {
			
			return ((BigDecimal) resultado).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
			
		} else if (resultado instanceof Double) {
			
			return new BigDecimal((Double) resultado).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
			
		} else if (resultado instanceof Long) {
			
			return ((Long) resultado).doubleValue();
			
		} else if (resultado instanceof Float) {
			
			return new BigDecimal((Float) resultado).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
			
		} else if (resultado instanceof Integer) {
			
			return ((Integer) resultado).doubleValue();
			
		}
		
		return null;
		
	}

}
