package fir

import chisel3._
import chisel3.util._
import chisel3.stage.{ChiselStage, ChiselGeneratorAnnotation}

object LowPassApp extends App {
  val bitWidth = 16
  val coefficients = Utils.readCoefficients(bitWidth, "lowpass.csv")
  val fir_verilog = Utils.emitVerilog(new FirFilter(bitWidth, coefficients), "lowpass")
  println(fir_verilog)
}
