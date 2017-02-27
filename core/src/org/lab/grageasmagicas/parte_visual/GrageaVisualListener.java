package org.lab.grageasmagicas.parte_visual;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.utils.DragListener;

import java.util.concurrent.BrokenBarrierException;

public class GrageaVisualListener extends DragListener {

    private GrageaVisual grageaVisual;
    private JuegoVisual juegoVisual;
    private int filaGragea;
    private int columnaGragea;
    private boolean wasDragged;
    private float primerX;
    private float primerY;

    public GrageaVisualListener(GrageaVisual grageaVisual, JuegoVisual juegoVisual, int filaGragea, int columnaGragea) {
        this.grageaVisual = grageaVisual;
        this.juegoVisual = juegoVisual;
        this.filaGragea = filaGragea;
        this.columnaGragea = columnaGragea;
        this.wasDragged = false;
    }

    @Override
    public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
        super.touchDown(event, x, y, pointer, button);
        primerX = x;
        primerY = y;
        return true;
    }

    @Override
    public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
        super.touchUp(event, x, y, pointer, button);
        try {
            if (juegoVisual.isInputGrageas()) {
                juegoVisual.desbrillarGrageas();
                if (grageaVisual.getTipo() == 100) {
                    juegoVisual.setInputGrageas(false);
                    juegoVisual.setInputMenus(false);
                    juegoVisual.setPrimerGrageaX(filaGragea);
                    juegoVisual.setPrimerGrageaY(columnaGragea);
                    juegoVisual.setSuperGrageaActivada(true);
                    juegoVisual.getBarrierRespuestaVisual().await();
                } else {
                    if (wasDragged && Math.abs(primerX - x) > grageaVisual.getWidth() || Math.abs(primerY - y) > grageaVisual.getHeight()) {
                        wasDragged = false;
                        juegoVisual.setInputGrageas(false);
                        juegoVisual.setInputMenus(false);
                        juegoVisual.setHayGrageaSeleccionada(true);
                        if (juegoVisual.getPrimerGrageaX() != -1) {
                            juegoVisual.getMatrizGrageasVisuales()
                                    [juegoVisual.getPrimerGrageaX()][juegoVisual.getPrimerGrageaY()].deseleccionar();
                        }
                        juegoVisual.setPrimerGrageaX(filaGragea);
                        juegoVisual.setPrimerGrageaY(columnaGragea);
                        if (!juegoVisual.isPoderMovDiagonalActivado()) {
                            if (Math.abs(primerX - x) > Math.abs(primerY - y)) {
                                if (primerX > x) {
                                    //izquierda
                                    if (columnaGragea > 0 &&
                                            juegoVisual.getMatrizGrageasVisuales()[filaGragea][columnaGragea - 1].getTipo() != 100) {
                                        juegoVisual.setSegundaGrageaX(filaGragea);
                                        juegoVisual.setSegundaGrageaY(columnaGragea - 1);
                                    }
                                } else {
                                    //derecha
                                    if (columnaGragea < juegoVisual.getMatrizGrageasVisuales()[0].length - 1 &&
                                            juegoVisual.getMatrizGrageasVisuales()[filaGragea][columnaGragea + 1].getTipo() != 100) {
                                        juegoVisual.setSegundaGrageaX(filaGragea);
                                        juegoVisual.setSegundaGrageaY(columnaGragea + 1);
                                    }
                                }
                            } else {
                                if (primerY < y) {
                                    //arriba
                                    if (filaGragea > 0 &&
                                            juegoVisual.getMatrizGrageasVisuales()[filaGragea - 1][columnaGragea].getTipo() != 100) {
                                        juegoVisual.setSegundaGrageaX(filaGragea - 1);
                                        juegoVisual.setSegundaGrageaY(columnaGragea);
                                    }
                                } else {
                                    //abajo
                                    if (filaGragea < juegoVisual.getMatrizGrageasVisuales().length - 1 &&
                                            juegoVisual.getMatrizGrageasVisuales()[filaGragea + 1][columnaGragea].getTipo() != 100) {
                                        juegoVisual.setSegundaGrageaX(filaGragea + 1);
                                        juegoVisual.setSegundaGrageaY(columnaGragea);
                                    }
                                }
                            }
                        } else {
                            if (primerX > x) { //izquierda
                                if (primerY > y) { //izquierda-abajo
                                    if (columnaGragea > 0 && filaGragea < juegoVisual.getMatrizGrageasVisuales().length - 1 &&
                                            juegoVisual.getMatrizGrageasVisuales()[filaGragea + 1][columnaGragea - 1].getTipo() != 100) {
                                        juegoVisual.setSegundaGrageaX(filaGragea + 1);
                                        juegoVisual.setSegundaGrageaY(columnaGragea - 1);
                                    }
                                } else { //izquierda-arriba
                                    if (filaGragea > 0 && columnaGragea > 0 &&
                                            juegoVisual.getMatrizGrageasVisuales()[filaGragea - 1][columnaGragea - 1].getTipo() != 100) {
                                        juegoVisual.setSegundaGrageaX(filaGragea - 1);
                                        juegoVisual.setSegundaGrageaY(columnaGragea - 1);
                                    }
                                }
                            } else { //derecha
                                if (primerY > y) { //derecha-abajo
                                    if (filaGragea < juegoVisual.getMatrizGrageasVisuales().length - 1 &&
                                            columnaGragea < juegoVisual.getMatrizGrageasVisuales()[0].length - 1 &&
                                            juegoVisual.getMatrizGrageasVisuales()[filaGragea + 1][columnaGragea + 1].getTipo() != 100) {
                                        juegoVisual.setSegundaGrageaX(filaGragea + 1);
                                        juegoVisual.setSegundaGrageaY(columnaGragea + 1);
                                    }
                                } else { //derecha-arriba
                                    if (columnaGragea < juegoVisual.getMatrizGrageasVisuales()[0].length - 1 && filaGragea > 0 &&
                                            juegoVisual.getMatrizGrageasVisuales()[filaGragea - 1][columnaGragea + 1].getTipo() != 100) {
                                        juegoVisual.setSegundaGrageaX(filaGragea - 1);
                                        juegoVisual.setSegundaGrageaY(columnaGragea + 1);
                                    }
                                }
                            }
                        }
                        if (juegoVisual.verificarAdyacentes() && !juegoVisual.isPoderMovDiagonalActivado() ||
                                juegoVisual.verificarDiagonales() && juegoVisual.isPoderMovDiagonalActivado()) {
                            juegoVisual.getBarrierRespuestaVisual().await();
                        } else {
                            Gdx.app.log("Check", "Movimiento invalido");
                            juegoVisual.limpiarPosGrageas();
                            juegoVisual.setHayGrageaSeleccionada(false);
                            juegoVisual.setInputGrageas(true);
                            juegoVisual.setInputMenus(true);
                        }
                    } else {
                        if (!grageaVisual.isSeleccionada()) {
                            if (!juegoVisual.isHayGrageaSeleccionada()) {
                                seleccionar();
                            } else {
                                juegoVisual.setInputGrageas(false);
                                juegoVisual.setInputMenus(false);
                                juegoVisual.setSegundaGrageaX(filaGragea);
                                juegoVisual.setSegundaGrageaY(columnaGragea);
                                if (juegoVisual.verificarAdyacentes() && !juegoVisual.isPoderMovDiagonalActivado() ||
                                        juegoVisual.verificarDiagonales() && juegoVisual.isPoderMovDiagonalActivado()) {
                                    juegoVisual.getBarrierRespuestaVisual().await();
                                } else {
                                    Gdx.app.log("Check", "Movimiento invalido");
                                    juegoVisual.getMatrizGrageasVisuales()
                                            [juegoVisual.getPrimerGrageaX()][juegoVisual.getPrimerGrageaY()].deseleccionar();
                                    seleccionar();
                                    juegoVisual.setInputGrageas(true);
                                    juegoVisual.setInputMenus(true);
                                }
                            }
                        } else {
                            grageaVisual.deseleccionar();
                            juegoVisual.setHayGrageaSeleccionada(false);
                            juegoVisual.limpiarPosGrageas();
                        }
                    }
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (BrokenBarrierException e) {
            e.printStackTrace();
        }
    }

    private void seleccionar() {
        grageaVisual.seleccionar();
        juegoVisual.setHayGrageaSeleccionada(true);
        juegoVisual.setPrimerGrageaX(filaGragea);
        juegoVisual.setPrimerGrageaY(columnaGragea);
        //hace brillar a las grageas con las que puede intercambiarse
        if (!juegoVisual.isPoderMovDiagonalActivado()) {
            juegoVisual.brillarGrageasAdyacentes(filaGragea, columnaGragea);
        } else {
            juegoVisual.brillarGrageasDiagonales(filaGragea, columnaGragea);
        }
    }

    @Override
    public void touchDragged(InputEvent event, float x, float y, int pointer) {
        super.touchDragged(event, x, y, pointer);
        wasDragged = true;
    }

    @Override
    public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
        super.enter(event, x, y, pointer, fromActor);
    }

    public int getFilaGragea() {
        return filaGragea;
    }

    public void setFilaGragea(int filaGragea) {
        this.filaGragea = filaGragea;
    }

    public int getColumnaGragea() {
        return columnaGragea;
    }

    public void setColumnaGragea(int columnaGragea) {
        this.columnaGragea = columnaGragea;
    }

    public void setFilaColumnaGragea(int filaGragea, int columnaGragea) {
        this.filaGragea = filaGragea;
        this.columnaGragea = columnaGragea;
    }

}
