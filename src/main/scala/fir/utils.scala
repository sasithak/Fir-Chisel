package fir

import chisel3._
import chisel3.util._
import chisel3.stage.{ChiselStage, ChiselGeneratorAnnotation}
import firrtl._
import firrtl.{EmittedFirrtlCircuitAnnotation, EmittedVerilogCircuitAnnotation}
import firrtl.annotations.DeletedAnnotation
import scala.io.Source

object Utils {
  def emitVerilog(gen: => RawModule): String =
    (new ChiselStage)
      .emitVerilog(gen, Array("--target-dir", "out/verilog/"))


  def emitFirrtl(gen: => RawModule): String =
    (new ChiselStage)
      .emitFirrtl(gen, Array("--target-dir", "out/firrtl/"))

  def readCoefficients(bitWidth: Int): Seq[SInt] = {
  try {
    val filePath = "in/coefficients.csv"
    val file_in = Source.fromFile(filePath)
    val coefficients = file_in.getLines().next().split(",").map(_.trim.toInt)
    file_in.close()

    val coefficientsSInt = coefficients.map(coeff => (coeff).toLong.S(bitWidth.W))
    coefficientsSInt
  } catch {
    case e: Exception =>
      Seq.empty[SInt]
  }
}
}
