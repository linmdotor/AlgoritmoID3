import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.scene.Scene;

import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.Stop;

import javafx.scene.control.Label;
import javafx.scene.text.Font;
import javafx.scene.control.TextField;

import javafx.event.EventHandler;
import javafx.event.ActionEvent;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URL;

public class ControladorGUI extends Application implements EventHandler<ActionEvent>{

	private Label labelLlantas,labelObjeto,labelGas,labelResultado,resultado;
	private TextField valorLlantas,valorObjeto,valorGas;
	private Button boton;

	public void start(Stage escenario){
				Group root = new Group();
				Scene scene= new Scene(root,530,350,Color.LIGHTSKYBLUE);
				escenario.setTitle("Algoritmo ID3");
				escenario.setScene(scene);


				//Grupo Llantas
				labelLlantas=new Label("¿Llantas Desgastadas?");
				labelLlantas.setTranslateX(20);
				labelLlantas.setTranslateY(30);
				labelLlantas.setFont(Font.font("BROADWAY",20));
				labelLlantas.setTextFill(Color.BLACK);
				root.getChildren().add(labelLlantas);

				valorLlantas=new TextField();
				valorLlantas.setPromptText("Introduce SI o NO");
				valorLlantas.setTranslateX(270);
				valorLlantas.setTranslateY(29);
				valorLlantas.setPrefColumnCount(20);
				root.getChildren().add(valorLlantas);
				//Acaba grupo llantas

				//Grupo Objeto
				labelObjeto=new Label("¿Obeto al frente?");
				labelObjeto.setTranslateX(20);
				labelObjeto.setTranslateY(80);
				labelObjeto.setFont(Font.font("BROADWAY",20));
				labelObjeto.setTextFill(Color.BLACK);
				root.getChildren().add(labelObjeto);

				valorObjeto=new TextField();
				valorObjeto.setPromptText("Introduce SI o No");
				valorObjeto.setTranslateX(270);
				valorObjeto.setTranslateY(80);
				valorObjeto.setPrefColumnCount(20);
				root.getChildren().add(valorObjeto);
				//Acaba grupo Objeto

				//Grupo Gas
				labelGas=new Label("¿Nivel de Gasolina?");
				labelGas.setTranslateX(20);
				labelGas.setTranslateY(130);
				labelGas.setFont(Font.font("BROADWAY",20));
				labelGas.setTextFill(Color.BLACK);
				root.getChildren().add(labelGas);

				valorGas=new TextField();
				valorGas.setPromptText("Introduce MUCHO o POCO");
				valorGas.setTranslateX(270);
				valorGas.setTranslateY(130);
				valorGas.setPrefColumnCount(20);
				root.getChildren().add(valorGas);
				//Acaba grupo Gas

				//boton accion
				boton=new Button("Oprime para obtener la acción a realizar");
				boton.setTranslateX(150);
				boton.setTranslateY(200);
				root.getChildren().add(boton);
				boton.setOnAction(this);
				//acaba boton

				//grupo Resultado
				labelResultado=new Label("La accion a realizar es: ");
				labelResultado.setTranslateX(20);
				labelResultado.setTranslateY(250);
				labelResultado.setFont(Font.font("BROADWAY",20));
				labelResultado.setTextFill(Color.BLACK);
				root.getChildren().add(labelResultado);

				resultado=new Label();
				resultado.setTranslateX(280);
				resultado.setTranslateY(250);
				resultado.setFont(Font.font("BROADWAY",20));
				resultado.setTextFill(Color.GREEN);
				root.getChildren().add(resultado);

				escenario.show();

	}

	public static void main(String[] args){
		launch(args);
	}

	public void handle(ActionEvent e){
		try{
			String llantas,objeto,gas;
			Object aux=e.getSource();
			Id3 algo=new Id3();
			llantas=valorLlantas.getText().toLowerCase();
			objeto=valorObjeto.getText().toLowerCase();
			gas=valorGas.getText().toLowerCase();
			Arbol arbol = algo.algoritmo(archivo2URL("carreras.txt"), "accion", " "); //le damos el nombre del archivo y a traves de la clase URL obtenemos la direccion, tambien le decimos la accion a realizar y el separador
			String[] atributos = {null,objeto,llantas,gas};
			String prediccion=arbol.predecirValor(atributos);

				if(aux.equals(boton)){
					System.out.println("Resultado: " +prediccion);

					if(prediccion.equals("pits")){
						resultado.setTextFill(Color.YELLOW);
						resultado.setText(prediccion);
					}
					if(prediccion.equals("frenar")){
						resultado.setTextFill(Color.RED);
						resultado.setText(prediccion);
					}
					if(prediccion.equals("esquivar")){
						resultado.setTextFill(Color.ORANGE);
						resultado.setText(prediccion);
					}
					if(prediccion.equals("avanzar")){
						resultado.setTextFill(Color.GREEN);
						resultado.setText(prediccion);
					}

				}

		}catch(IOException ex){
			System.out.println("ERROR");
		}
	}
	public static String archivo2URL(String nombreArchivo) throws UnsupportedEncodingException{
			URL direccion = ControladorGUI.class.getResource(nombreArchivo);
			 return java.net.URLDecoder.decode(direccion.getPath(),"UTF-8");
	}
}