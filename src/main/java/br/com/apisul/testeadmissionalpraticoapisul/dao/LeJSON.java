package br.com.apisul.testeadmissionalpraticoapisul.dao;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import br.com.apisul.testeadmissionalpraticoapisul.model.Info;

public class LeJSON {

	public List<Info> leJSON(File file) {

		JSONObject jsonObject = new JSONObject();
		JSONParser parser = new JSONParser();

		List<Info> listaInfo = new ArrayList<Info>();

		try {
			Object json = (Object) parser.parse(new FileReader(file));

			JSONArray array = (JSONArray) json;

			Iterator it = array.iterator();
			while (it.hasNext()) {
				listaInfo.add(parseInfo((JSONObject) it.next()));
			}

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return listaInfo;
	}

	public static Info parseInfo(JSONObject infoJson) {
		Info tmpInfo = new Info();
		tmpInfo.setAndar(Integer.parseInt(infoJson.get("andar").toString()));
		tmpInfo.setElevador(infoJson.get("elevador").toString().toCharArray()[0]);
		tmpInfo.setTurno(infoJson.get("turno").toString().toCharArray()[0]);

		return tmpInfo;
	}
}
