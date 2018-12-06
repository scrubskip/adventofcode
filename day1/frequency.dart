import "dart:async";
import 'dart:convert';
import "dart:io";


void main(List<String> args) {
  analyzeFrequency(args[0]);
}

Future analyzeFrequency(String path) async {
  var current_frequency = 0;
  Stream lines = new File(path)
          .openRead()
          .transform(utf8.decoder)
          .transform(const LineSplitter());
  try {
    await for (var line in lines) {
      // parse as number
      current_frequency += int.parse(line);
    }
  } catch (_) {

  }
  stdout.writeln(current_frequency);
}