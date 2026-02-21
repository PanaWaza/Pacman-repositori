
package com.mycompany.pacmanproyect;
import java.util.Scanner;
import java.util.Random;
import java.util.ArrayList;

public class PacmanProyect {
    
    
    // crear objeto para guardar historial usando la clase
    // crear un arraylist con el formato del objeto
    static ArrayList<PacmanProyect> listado = new ArrayList <>();
    
    String nombre;
    int puntaje;
    public PacmanProyect (String nombre,int puntaje) {
    this.nombre = nombre;
    this.puntaje = puntaje;
    }
    
    public static void GuardarPartida(String nombre,int puntaje){
        PacmanProyect nuevo = new PacmanProyect(nombre, puntaje);
        listado.add(nuevo);
    }
        
        
    // objetos glovales para el transcurso de la partida
    static Scanner leer = new Scanner(System.in);
    static int filas, columnas;
    static int [][]tablero ;
    static int filaPacman,columnaPacman;
    static int Puntaje;
    static int salida; // terminar programa
    static int VidasPacman =3;
    static String nombreUsuario ;
    static int CantidadObjetos=0;
    
   
    
    public static void Usuario(){
        System.out.println(" Bienbenido Jugador por favor ingresa tu nombre ");
        System.out.println();
        System.out.println(" ! ten en cuenta que si no terminas tu partida  ");
        System.out.println(" no se te agregara al historial ! ");
        System.out.println("");
        System.out.print("nombre -> "); nombreUsuario = scp();
    }
    
    public static void MostrarHistorial (){
        if (listado.size() == 0) {
            System.out.println("    Nombre         |    Puntaje ");
            System.out.println(" ! No hay partidas registradas ");
            for (int i = (listado.size()-1); i >= 0; i--) { 
            System.out.println("-> "+ listado.get(i).nombre + "          -> "+ listado.get(i).puntaje);
        }
        }
    }
    
    public static String scp(){
        leer.nextLine();
        return leer.nextLine();
    }
    
    public static void posicionInicialPacman(){
        System.out.println(" ");
        System.out.println(" - Por favor ingrese fila y columna donde pondra a Pacman ");
        System.out.print(" fila -> ");
        filaPacman = sc();
        filaPacman--; // esto es para ajustar el valor del usario con el de los indies
        System.out.println("");
        System.out.print(" columna -> ");
        columnaPacman = sc();
        columnaPacman--; // esto es para ajustar el valor del usario con el de los indies
    }
    
    public static int sc (){
        return leer.nextInt();
    }

    public static int CrearTablero(int lenght){
        switch (lenght) {
            case 1 -> {
                filas = 5;
                columnas =6;
            }
            case 2 -> {
                filas = 10;
                columnas = 10;
            }
            default -> {
                System.out.println(" El tamaño no es valido");
                lenght = sc(); // pedir el nuevo valor por si no es ni 1 ni 2
                return CrearTablero(lenght);
            }
        }
        tablero = new int[filas][columnas];
        return 0;
    }
    
    public static void ValorInicialTablero(){
        for (int i = 0; i < filas; i++) {
            for (int j = 0; j < columnas; j++) {
                tablero[i][j] = 0;// cada casilla empieza basilla 
            }
        }
        tablero[filaPacman][columnaPacman]=5; // definir al pacman
    }
    
    public static void MostrarTablero(){
        /*
        Fantasma @ 1
        Premio Simple 0 2
        Premio Especial $ 3
        Pared X 4
        PACMAN < 5
        
        No se definen los valore iniciales del tablero para reutilizar este bloque
     */
        System.out.println("");
        System.out.println("  -- Tablero --");
        System.out.println("");
        System.out.println("--------------------");
        for (int i = 0; i < filas; i++) { // Filas
            for (int j = 0; j < columnas; j++) { //Columnas
                switch (tablero[i][j]) {
                    case 1 -> {
                        System.out.print("@"); // fantasma
                    }
                    case 2 -> {
                        System.out.print("0"); // premio simple
                    }
                    case 3 -> {
                        System.out.print("$"); // premio especial 
                    }
                    case 4 -> {
                        System.out.print("x"); // pared 
                    }
                    case 5 -> {
                        System.out.print("<"); // pacman
                    }
                    default -> {
                        System.out.print(" ");
                    }
                }
                System.out.print("|");  // division enre espacios

            }
            System.out.println();
        }
        System.out.println("--------------------");
        System.out.println("");// direfenciar
    }
    
 
    
    public static void ObjetosRandom(){
        ValorInicialTablero(); // inicialmente vacio
        // calcular porcentual de objetos
      int fantasmas = (int)(((filas-1)*(columnas-1))*0.15) ;
      int PremioSimple = (int)(((filas-1)*(columnas-1))*0.2);
      int PremioEspecial = (int)(((filas-1)*(columnas-1))*0.1);
      int Paredes = (int)(((filas-1)*(columnas-1))*0.3);
      CantidadObjetos = PremioSimple + PremioEspecial;
      //modulo asignacion de valor aleatorio
      int numero ;
      //modulos de fila y columna
      int f,c;
      do{
          numero = ValorAleatorio(4);
          // generar una fila y columna al azar para poner un objeto
          f = ValorAleatorio(filas-2);
          c = ValorAleatorio(columnas-2);
           // este if es por si se trata de poner un objeto sobre pacman
           // verificar que la casilla este basilla antes de colocar un objeto
          if (tablero[f][c]== 0 && !(f==filaPacman && c==columnaPacman)){
              if (fantasmas>0 && numero == 1) {
                  tablero[f][c]=numero;// asignar valor
                  fantasmas--;//reducir contador
              }
              if (PremioSimple>0 && numero == 2) {
                  tablero[f][c]=numero;// asignar valor
                  PremioSimple--;//reducir contador
              }
              if (PremioEspecial>0 && numero == 3) {
                  tablero[f][c]=numero;// asignar valor
                  PremioEspecial--;//reducir contador
              }
              if (Paredes>0 && numero ==4) {
                  tablero[f][c]=numero;
                  Paredes--;
              }
          }
      }while(fantasmas>0 || PremioSimple>0 || PremioEspecial>0 || Paredes>0);     
    }
    
    public static int ValorAleatorio(int valor){
        Random random = new Random();
        return random.nextInt(valor+1); // limite inclusimo esto para no estar pensando en la cantidad maxima
    }
    
    public static int MenuMovimientoPacman(){
        System.out.println(" ");
        System.out.println(" --- Menu Movimiento pacman --- ");
        System.out.println("1. Arriba ");
        System.out.println("2. Abajo ");
        System.out.println("3. Derecha ");
        System.out.println("4. Izquierda ");
        System.out.println("5. Pausar ");
        System.out.println("6. Salir");
        System.out.print(" -> ");
        int opcion = sc();
        switch(opcion){
            case 1->{// arriba 
                filaPacman = MovimientoArriba(filaPacman,columnaPacman);
                tablero[filaPacman][columnaPacman]=5;
            }
            case 2->{// abajo
                filaPacman = MovimientoAbajo(filaPacman,columnaPacman);
                tablero[filaPacman][columnaPacman]=5;
            }
            case 3->{// derecha
                columnaPacman = MovimientoDerecha(filaPacman,columnaPacman);
                tablero[filaPacman][columnaPacman]=5;
            }
            case 4->{// izquierda
                columnaPacman = MovimientoIzquierda(filaPacman,columnaPacman);
                tablero[filaPacman][columnaPacman]=5;
            }
            case 5->{// pausar
                int pausa;
                System.out.println("");
                System.out.println(" ! Juego Pausado !");
                System.out.println(" 1 para reanudar ");
                System.out.println(" cualquier otro para terminar el juego ");
                System.out.println(" -> "); pausa = sc();
                switch(pausa){
                    case 1->{
                        System.out.println(" ! Se reanudo el juego ");
                        MostrarTablero();
                        MenuMovimientoPacman();
                    }
                    default ->{
                        transcursoPartida();
                    }
                }
            }
            case 6->{// salir
                System.out.println(" Hasta la proxima ");
                transcursoPartida();
            }
            default->{
                System.out.println("");
                System.out.println(" ! Opcion Invalida ");
                return MenuMovimientoPacman();
            }
        }
        if (CantidadObjetos ==0){
            System.out.println(" Felicidades has ganado !!! ");
            GuardarPartida(nombreUsuario,Puntaje);
            transcursoPartida();
        }
        return 0;
    }
    
    public static int MovimientoArriba(int fp, int cp){  // parametro fila pacman inicialmente
        boolean apoyo = true;// apoyo por si se toca una pared 
        if ((fp-1) < 0) { // por si se sale de los parametros
            apoyo = ConfiguracionObjetos(filas-1,cp);
            if (apoyo == false) {
                return fp;
            }
            tablero[fp][cp] =0;
            return filas-1;
        }
        apoyo = ConfiguracionObjetos(fp-1,cp);
        if (apoyo == false) {
            return fp;
        }
        tablero[fp][cp] =0;
        return fp -1;
    }
    
    public static int MovimientoAbajo(int fp , int cp){  // parametro fila pacman inicialmente
        boolean apoyo= true;
        if ((fp+1)== filas) {// evitar salir de rango y paredes
            apoyo = ConfiguracionObjetos(0,cp);
            if (apoyo == false) {
             return fp;   
            }
            tablero[fp][cp] =0;
            return 0;
        }
        apoyo = ConfiguracionObjetos(fp+1,cp);
        if (apoyo == false) {
            return fp;
        }
        tablero[fp][cp] =0;
        return fp+1;
    }
    
    public static int MovimientoDerecha(int fp ,int cp){  // parametro fila pacman inicialmente
        boolean apoyo= true;
        if ((cp+1)== columnas) {// evitar salir de rango y paredes
            apoyo = ConfiguracionObjetos(fp,0); 
            if (apoyo == false) {
                return cp;
            }
            tablero[fp][cp] =0;
            return 0;
        }
        apoyo = ConfiguracionObjetos(fp,cp+1);
        if (apoyo == false) {
            return cp;
        }
        tablero[fp][cp] =0;
        return cp+1;
    }
    
    public static int MovimientoIzquierda(int fp ,int cp){  // parametro fila pacman inicialmente
        boolean apoyo= true;
        if ((cp-1) < 0) { // por si se sale de los parametros
            apoyo = ConfiguracionObjetos(fp,columnas-1);
            if (apoyo==false) {
                return cp;
            }
            ConfiguracionObjetos(fp,columnas-1);
            tablero[fp][cp] =0;
            return columnas-1;
        }
        apoyo = ConfiguracionObjetos(fp,cp-1);
        if (apoyo == false) {
            return cp;
        }
        tablero[fp][cp] =0;
        return cp -1;
    }
    
    // recuerda agregar al menu del usuario sus vidas y puntaje 
    public static boolean ConfiguracionObjetos (int fp, int cp){
        if (tablero[fp][cp] != 0 ) {
            switch (tablero[fp][cp]){
                case 1->{ //fantasma
                    VidasPacman--; 
                    System.out.println();
                    System.out.println(" ! Tocaste un fantasma ");
                    System.out.println();
                    return true;
                }
                case 2->{// premio simple 10 puntos
                    Puntaje = Puntaje+10;
                    CantidadObjetos--;
                    return true;
                }
                case 3->{// premio especial 15 puntos
                    Puntaje = Puntaje+15;
                    CantidadObjetos--;
                    return true;
                }
                case 4->{// pared
                    System.out.println(" ! No se puede mover a pacman en la posicion indicada ");
                    return false;
                }
            }   
        }
        return true;
    }
    
    
    
    public static int MenuInicio(){
        System.out.println("-------------------------------------");
        System.out.println("| MENU INICIO                       |");
        System.out.println("|1. Iniciar juego                   |");
        System.out.println("|2. Historial Partidas              |");
        System.out.println("|3. Salir                           |");
        System.out.println("-------------------------------------");
        int opcion;
        System.out.print(" -> " ); opcion = sc();
        return opcion;
    }
    
    public static int MenuTablero(){
        System.out.println("-------------------------------------");
        System.out.println("| Por favor seleccione el tablero   |");
        System.out.println("|1. Tablero pequeño (5x6)           |");
        System.out.println("|2. Tablero grande (10x10)          |");
        System.out.println("-------------------------------------");
        System.out.print("->"); int tamaño = sc();
        return tamaño;
    }
    
    public static int opcionesMenuInicio(int opcionInicio){
        switch(opcionInicio){
            case 1->{// iniciar juego
                Usuario();
                // opciones tablero
                int opcionTablero;
                opcionTablero = MenuTablero();
                CrearTablero(opcionTablero);
                // ingresar posicion del pacman
                // -> hacer una validacion por si se ingresa al pacman fuera de rango !!!!!!
                posicionInicialPacman();
                //valores tablero
                ObjetosRandom(); // Asignar objetos
                do{
                    MostrarTablero();
                    MenuMovimientoPacman();
                    System.out.println(" punteo -> " + Puntaje);
                    System.out.println(" vidas -> " + VidasPacman);
                }while (!(VidasPacman==0) || !(CantidadObjetos==0) ); 
                // cuando se termine la partida agregar al jugador al historial
                GuardarPartida(nombreUsuario,Puntaje);
            }
            case 2->{// HIstorial partidas
                MostrarHistorial();
            }
            case 3->{// salir
                System.out.println(" Hasta la proxima "); 
                return 3;
            }
            default->{
                System.out.println(" ! Opcion Invalida ");
                return opcionesMenuInicio(opcionInicio);
            }
        }
        return 0; // esto no sirve pa nada
    }
    
    public static void transcursoPartida(){
        int opcionInicio;
        do {
            opcionInicio = MenuInicio();
            salida = opcionesMenuInicio(opcionInicio);
        }while (!(salida==3)); 
    }
    
    public static void main(String[] args) {
        transcursoPartida();
    }
}
