package fir

import chisel3._
import chisel3.util._
import chisel3.stage.{ChiselStage, ChiselGeneratorAnnotation}

object BandPassApp extends App {
  val bitWidth = 16
  val coefficients = Utils.readCoefficients(bitWidth, "bandpass.csv")
  val fir_verilog = Utils.emitVerilog(new FirFilter(bitWidth, coefficients), "bandpass")
  println(fir_verilog)
}
