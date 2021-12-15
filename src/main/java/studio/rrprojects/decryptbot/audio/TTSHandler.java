package studio.rrprojects.decryptbot.audio;

import com.google.cloud.texttospeech.v1.*;
import com.google.protobuf.ByteString;
import studio.rrprojects.util_library.DebugUtils;

import java.io.*;

public class TTSHandler {
    TextToSpeechClient textToSpeechClient;
    SynthesisInput input;
    VoiceSelectionParams voice;
    AudioConfig audioConfig;

    public TTSHandler() {
        initSpeechSystem();
    }

    public void initSpeechSystem() {
        DebugUtils.ProgressNormalMsg("INITIALIZING TTS CLIENT");

        // Instantiates a client
        try {
            textToSpeechClient = TextToSpeechClient.create();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Build the voice request, select the language code ("en-US") and the ssml voice gender

        //TODO Create Voice Profiles

        voice = VoiceSelectionParams.newBuilder()
                //Specify a voice beyond just language code - Allows selection of Wavenet (Premium) voices over standard ones (I think Wavenet voices are selected automatically)
                .setName("en-US-Wavenet-I")
                .setLanguageCode("en-US")
                .setSsmlGender(SsmlVoiceGender.MALE)
                .build();

        // Select the type of audio file you want returned
        audioConfig = AudioConfig.newBuilder()
                .setAudioEncoding(AudioEncoding.MP3)
                .setPitch(-9)
                //.setSpeakingRate(0.8)
                .build();
    }

    public String speak(String text) throws IOException {
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
        String outputFile = "output.mp3";
        try (OutputStream out = new FileOutputStream(outputFile)) {
            out.write(audioContents.toByteArray());
            System.out.println("Audio content written to file: " + outputFile);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return outputFile;
    }

    /*
     * Everything below here is the system needed to convert a TTS InputStream to a file so that lavaplayer can handle it
     * */

    public byte[] inputStreamToByteArray(InputStream inStream) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        byte[] buffer = new byte[8192];
        int bytesRead;
        while ((bytesRead = inStream.read(buffer)) > 0) {
            byteArrayOutputStream.write(buffer, 0, bytesRead);
        }
        return byteArrayOutputStream.toByteArray();
    }

    public void byteArrayToFile(byte[] byteArray, String outFilePath) throws IOException{
        FileOutputStream fos = new FileOutputStream(outFilePath);
        fos.write(byteArray);
        fos.close();
    }
}