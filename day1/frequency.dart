import "dart:async";
import 'dart:convert';
import "dart:io";

void main(List<String> args) {
  new File(args[0])
      .openRead()
      .transform(utf8.decoder)
      .transform(const LineSplitter())
      .transform(StreamTransformer.fromHandlers(
          handleData: (String event, EventSink output) {
        output.add(int.parse(event));
      }))
      .toList()
      .then((list) => analyzeFrequency(list));
}

analyzeFrequency(List frequencies) {
  var currentFrequency = 0;
  Set seenFrequencies = new Set();
  var firstSeen = null;
  while (firstSeen == null) {
    for (var frequency in frequencies) {
      // parse as number
      currentFrequency += frequency;
      if (firstSeen == null && seenFrequencies.contains(currentFrequency)) {
        firstSeen = currentFrequency;
      }
      seenFrequencies.add(currentFrequency);
    }
  }

  stdout.writeln(currentFrequency);
  stdout.writeln(firstSeen);
}
