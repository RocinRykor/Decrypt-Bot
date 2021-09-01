package studio.rrprojects.decryptbot.audio;

import com.google.cloud.texttospeech.v1.*;
import com.google.protobuf.ByteString;
import studio.rrprojects.util_library.DebugUtils;

import java.io.*;

public class TTSHandler {
    static TextToSpeechClient textToSpeechClient;
    static SynthesisInput input;
    static VoiceSelectionParams voice;
    static AudioConfig audioConfig;

    public TTSHandler() {
        initSpeechSystem();
    }

    public static void initSpeechSystem() {
        DebugUtils.ProgressNormalMsg("INITIALIZING TTS CLIENT");

        // Instantiates a client
        try {
            textToSpeechClient = TextToSpeechClient.create();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Build the voice request, select the language code ("en-US") and the ssml voice gender
        // ("neutral")
        voice = VoiceSelectionParams.newBuilder()
                .setName("en-GB-Standard-D") //Specify a voice beyond just language code - Allows selection of Wavenet (Premium) voices over standard ones (I think Wavenet voices are selected automatically)
                .setLanguageCode("en-GB")
                .setSsmlGender(SsmlVoiceGender.MALE)
                .build();

        // Select the type of audio file you want returned
        audioConfig = AudioConfig.newBuilder()
                .setAudioEncoding(AudioEncoding.MP3)
                .build();

		/*
		 * Shows list of voices
		 *
		// Builds the text to speech list voices request
	    ListVoicesRequest request = ListVoicesRequest.getDefaultInstance();

	    // Performs the list voices request
	    ListVoicesResponse response = textToSpeechClient.listVoices(request);
	    List<Voice> voices = response.getVoicesList();

	    for (Voice voice : voices) {
	      // Display the voice's name. Example: tpc-vocoded
	      System.out.format("Name: %s\n", voice.getName());

	      // Display the supported language codes for this voice. Example: "en-us"
	      List<ByteString> languageCodes = voice.getLanguageCodesList().asByteStringList();
	      for (ByteString languageCode : languageCodes) {
	        System.out.format("Supported Language: %s\n", languageCode.toStringUtf8());
	      }

	      // Display the SSML Voice Gender
	      System.out.format("SSML Voice Gender: %s\n", voice.getSsmlGender());

	      // Display the natural sample rate hertz for this voice. Example: 24000
	      System.out.format("Natural Sample Rate Hertz: %s\n\n",
	          voice.getNaturalSampleRateHertz());
	    }
	    */

    }

    public static String speak(String text) throws IOException {
        System.out.println("SPEAKING:" + text);


        input = SynthesisInput.newBuilder().setText(text).build();

        // Perform the text-to-speech request on the text input with the selected voice parameters and
        // audio file type

        DebugUtils.VaraibleMsg("INPUT: " + input);
        DebugUtils.VaraibleMsg("VOICE: " + voice);
        DebugUtils.VaraibleMsg("AUDIO CONFIG: " + audioConfig);
        DebugUtils.VaraibleMsg("TTS CLIENT: " + textToSpeechClient);

        SynthesizeSpeechResponse response = textToSpeechClient.synthesizeSpeech(input, voice, audioConfig);

        // Get the audio contents from the response
        ByteString audioContents = response.getAudioContent();

        // Write the response to the output file.

        try (OutputStream out = new FileOutputStream("output.mp3")) {
            out.write(audioContents.toByteArray());
            System.out.println("Audio content written to file \"output.mp3\"");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return "output.mp3";
    }

    /*
     * Everything below here is the system needed to convert a TTS InputStream to a file so that lavaplayer can handle it
     * */

    public static byte[] inputStreamToByteArray(InputStream inStream) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        byte[] buffer = new byte[8192];
        int bytesRead;
        while ((bytesRead = inStream.read(buffer)) > 0) {
            baos.write(buffer, 0, bytesRead);
        }
        return baos.toByteArray();
    }

    public static void byteArrayToFile(byte[] byteArray, String outFilePath) throws IOException{
        FileOutputStream fos = new FileOutputStream(outFilePath);
        fos.write(byteArray);
        fos.close();
    }
}