package fir

import chiseltest._
import org.scalatest.flatspec.AnyFlatSpec
import chisel3._
import org.scalatest.freespec.AnyFreeSpec
import chisel3.experimental.BundleLiterals._

class FirFilterTest extends AnyFlatSpec with ChiselScalatestTester {
  "FirFilter" should "PASS" in {
    val bitWidth = 16

    val coeffs = Seq(
      621.S(bitWidth.W), 1252.S(bitWidth.W), 955.S(bitWidth.W), -464.S(bitWidth.W), -1427.S(bitWidth.W),
      -442.S(bitWidth.W), 1279.S(bitWidth.W), 815.S(bitWidth.W), -2028.S(bitWidth.W), -2978.S(bitWidth.W),
      1849.S(bitWidth.W), 9985.S(bitWidth.W), 14052.S(bitWidth.W), 9985.S(bitWidth.W), 1849.S(bitWidth.W),
      -2978.S(bitWidth.W), -2028.S(bitWidth.W), 815.S(bitWidth.W), 1279.S(bitWidth.W), -442.S(bitWidth.W),
      -1427.S(bitWidth.W), -464.S(bitWidth.W), 955.S(bitWidth.W), 1252.S(bitWidth.W),621.S(bitWidth.W)
    )

    test(new FirFilter(bitWidth, coeffs)) { c =>

      // To simulate a sine wave of 3 cycles
      val inputs = Seq(
        0.S, 23166.S, 32767.S, 23166.S, 0.S, -23166.S, -32768.S, -23166.S,
        0.S, 23166.S, 32767.S, 23166.S, 0.S, -23166.S, -32768.S, -23166.S,
        0.S, 23166.S, 32767.S, 23166.S, 0.S, -23166.S, -32768.S, -23166.S
      )

      // Expected outputs of the FIR filter
      val expectedOutputs = Seq(
        0.S, 14386086.S, 49352139.S, 77533900.S, 49547293.S, -40524326.S, -117099665.S, -95446734.S,
        1001663.S, 49878561.S, 540937.S, -6280916.S, 221906448.S, 645662149.S, 922528801.S, 701824145.S,
        999814.S, -747399325.S, -1039099553.S, -692478191.S, 49544165.S, 729480385.S, 971343458.S, 666331319.S
      )

      for ((input, expectedOutput) <- inputs.zip(expectedOutputs)) {
        c.io.in.poke(input)
        c.clock.step(1)
        c.io.out.expect(expectedOutput)
      }
    }
  }
}
