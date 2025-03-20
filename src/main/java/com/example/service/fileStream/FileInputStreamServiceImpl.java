package com.example.service.fileStream;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import org.springframework.stereotype.Service;

@Service
public class FileInputStreamServiceImpl implements FileInputStreamService {
  @Override
  public long randomNBitFromFile(int bitSize, String fileName) {
    // byte[] fakeFileData = new byte[]{(byte) 0b11111111, (byte) 0b11111111, (byte) 0b01000001};
    try (FileInputStream file = new FileInputStream(fileName)) {

      StringBuilder bits = new StringBuilder();

      boolean firstBit = true;
      int byteRead;
      while ((byteRead = file.read()) != -1 && bits.length() < bitSize) {
        System.out.println("byte: " + Integer.toBinaryString(byteRead));
        String binaryString =
            String.format("%8s", Integer.toBinaryString(byteRead)).replace(' ', '0');

        if (firstBit) {
          if (binaryString.isEmpty()) continue;
          while (binaryString.charAt(0) == '0') {
            binaryString = binaryString.substring(1); // ตัดบิตแรก
          }
          firstBit = false;
        }
        bits.append(binaryString);
      }

      if (bits.length() > bitSize) {
        bits.setLength(bitSize);
      }

      System.out.println("Random bit Successful: " + bits);
      return Long.parseLong(bits.toString(), 2);
    } catch (FileNotFoundException ex) {
      System.out.println("File not found: " + ex.getMessage());
    } catch (IOException ex) {
      System.out.println("IOException occurred: " + ex.getMessage());
    }
    return -1;
  }
}
