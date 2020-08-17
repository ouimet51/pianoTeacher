/*
 * Copyright 2018 Google LLC
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example;

import com.datastore.Chords;
import com.google.actions.api.ActionRequest;
import com.google.actions.api.ActionResponse;
import com.google.actions.api.DialogflowApp;
import com.google.actions.api.ForIntent;
import com.google.actions.api.response.ResponseBuilder;
import com.google.api.services.actions_fulfillment.v2.model.*;
import java.text.MessageFormat;
import java.util.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import static com.example.options.WELCOME_SUGGESTIONS;
import com.datastore.Scales;

/**
 * Implements all intent handlers for this Action. Note that your App must extend from DialogflowApp
 * if using Dialogflow or ActionsSdkApp for ActionsSDK based Actions.
 */
public class MyActionsApp extends DialogflowApp {

  private static final Logger LOGGER = LoggerFactory.getLogger(MyActionsApp.class);
  Scales scalesData = new Scales();
  Chords chordData = new Chords();
  public static List ScaleObjectHolder;
  public static List ChordObjectHolder;

  @ForIntent("Default Welcome Intent")
  public ActionResponse welcomeIntent(ActionRequest request) {
    LOGGER.info("Welcome intent start.");
    Map<String, Object> conversationData = request.getConversationData();
    conversationData.put("RECEIVED_SCALE_STARTER_PROMPT", false);
    conversationData.put("RECEIVED_CHORD_STARTER_PROMPT", false);
    ResourceBundle responses = ResourceBundle.getBundle("responses");
    ResponseBuilder responseBuilder = getResponseBuilder(request);
    String welcome = responses.getString("welcome1");
    responseBuilder.add(welcome);
    responseBuilder.addSuggestions(WELCOME_SUGGESTIONS);
    LOGGER.info("Welcome intent end.");
    return responseBuilder.build();
  }

  @ForIntent("Chord Practice Intent")
  public ActionResponse chordPractice(ActionRequest request) {
    LOGGER.info("Chord Practice intent start.");
    ResourceBundle responses = ResourceBundle.getBundle("responses");
    ResponseBuilder responseBuilder = getResponseBuilder(request);
    Map<String, Object> conversationData = request.getConversationData();

    if ((Boolean) conversationData.get("RECEIVED_CHORD_STARTER_PROMPT") != true) {
      conversationData.put("RECEIVED_CHORD_STARTER_PROMPT", true);
      responseBuilder.add(responses.getString("init_chord_practice"));
    }


    ChordObjectHolder = chordData.getRandomChordObject();
    String chordMessage = MessageFormat.format(responses.getString("chord_practice"), ChordObjectHolder.get(0));
    responseBuilder.add("WHY THE FUCK YOU LYING");
    responseBuilder.add(chordMessage);
    responseBuilder.addSuggestions(options.CHORD_SUGGESTIONS);
    LOGGER.info("Chord Practice intent ended.");
    return responseBuilder.build();
  }

  @ForIntent("Scale Practice Intent")
  public ActionResponse scalePractice(ActionRequest request) {
    LOGGER.info("Scale Practice intent start.");
    Map<String, Object> conversationData = request.getConversationData();
    ResponseBuilder responseBuilder = getResponseBuilder(request);
    ResourceBundle responses = ResourceBundle.getBundle("responses");


    if ((Boolean) conversationData.get("RECEIVED_SCALE_STARTER_PROMPT") != true) {
      conversationData.put("RECEIVED_SCALE_STARTER_PROMPT", true);
      responseBuilder.add(responses.getString("init_scale_practice"));
    }

    ScaleObjectHolder = scalesData.getRandomScaleObject();
    String scaleMessage = MessageFormat.format(responses.getString("scale_practice"), ScaleObjectHolder.get(0));
    responseBuilder.add(scaleMessage);
    responseBuilder.add("TESTING");
    responseBuilder.addSuggestions(options.SCALE_SUGGESTIONS);
    LOGGER.info("Scale Practice intent end.");
    return responseBuilder.build();
  }

  @ForIntent("Scale Practice Intent - help")
  public ActionResponse scaleHelp(ActionRequest request) {
    LOGGER.info("Help intent start.");

    ResponseBuilder responseBuilder = getResponseBuilder(request);
    responseBuilder
            .add("Hope this helps")
            .add(new BasicCard()
                    .setTitle((String) ScaleObjectHolder.get(0))
                    .setImage(new Image()
                            .setUrl((String) ScaleObjectHolder.get(1)))
                    .setButtons(
                            new ArrayList<Button>(
                                    Arrays.asList(
                                            new Button()
                                                    .setTitle("Hear the scale")
                                                    .setOpenUrlAction(
                                                            new OpenUrlAction().setUrl("https://assistant.google.com"))))));
    return responseBuilder.build();
  }

  @ForIntent("Chord Practice Intent - help")
  public ActionResponse chordHelp(ActionRequest request) {
    LOGGER.info("Chord Help intent start.");
    ResponseBuilder responseBuilder = getHelpResponse(request, ChordObjectHolder);
    return responseBuilder.build();
  }

  @ForIntent("Metronome Intent")
  public ActionResponse metronome(ActionRequest request) {
    ResponseBuilder responseBuilder = getResponseBuilder(request);
    String userInput = (String) request.getRawText();
    String metronomeSetting = userInput.replaceAll("[^0-9]", "");


    if (Integer.valueOf(metronomeSetting) > 140)
      responseBuilder.add("That is a bit fast, " +
              "setting your metronome at half the speed will make for a better practice session");
    while (Integer.valueOf(metronomeSetting) > 140)
        metronomeSetting = String.valueOf(Integer.valueOf(metronomeSetting) / 2);

    if (Integer.valueOf(metronomeSetting) < 50)
      responseBuilder.add("That is a bit slow, " +
              "setting your metronome at twice the speed will make for a better practice session");

    while (Integer.valueOf(metronomeSetting) < 50)
      metronomeSetting = String.valueOf(Integer.valueOf(metronomeSetting) * 2);

    // sets the metronome to the nearest multiple of 5
    metronomeSetting= String.valueOf(5*(Math.round(Integer.valueOf(metronomeSetting)/5)));


    responseBuilder
            .add("Nice! Using a metronome will make you a better musician!")
            .add(
                    new MediaResponse()
                            .setMediaObjects(
                                    new ArrayList<MediaObject>(
                                            Arrays.asList(
                                                    new MediaObject()
                                                            .setName("Metronome")
                                                            .setDescription(metronomeSetting)
                                                            .setContentUrl(
                                                                    "https://storage.googleapis.com/piano-assistant-static/static/metronome/"+ metronomeSetting + "BPM.mp3")
                                                            .setIcon(
                                                                    new Image()
                                                                            .setUrl(
                                                                                    "https://lh3.googleusercontent.com/proxy/Ag_pmFHK7Sur6V8Oj7p--dZy_NuHBqIZYXUKGb2ilFUB8wLQ_SaTxozbXapaR_NsIgp2I5UepzfO684vQgVI3s4qtWspCOTKzk5FvpGm2jYGxoImhmfD1C0iRas2Q63d0H_LcWvdSQOof7yUqq6-eKi-IxBVrioGZH8")
                                                                            .setAccessibilityText("Album cover of an ocean view")))))
                            .setMediaType("AUDIO"));
    responseBuilder.addSuggestions(new String[]{
            "Set metronome to " + String.valueOf(Integer.valueOf(metronomeSetting) + 5),
            "Set metronome to " + String.valueOf(Integer.valueOf(metronomeSetting) - 5)});
    return responseBuilder.build();
  }

  @ForIntent("Backing Track Intent")
  public ActionResponse backingTrack(ActionRequest request) {
    ResponseBuilder responseBuilder = getResponseBuilder(request);
    Map<String, Object> conversationData = request.getConversationData();
    String genre = (String) request.getParameter("music-genre");
    conversationData.put("GENRE", genre);
    ResourceBundle responses = ResourceBundle.getBundle("responses");

    String contentUrl;
    String contentName;

    switch (genre){
      case "jazz":
        contentUrl = responses.getString("jazz_url");
        contentName = responses.getString("jazz_name");
        break;
      case "rock":
        contentUrl = responses.getString("rock_url");
        contentName = responses.getString("rock_name");
        break;
      default:
        throw new IllegalStateException("Unexpected value: " + genre);
    }

    responseBuilder
            .add(responses.getString("encouragement"))
            .add(new MediaResponse()
                    .setMediaObjects(
                            new ArrayList<MediaObject>(
                                    Arrays.asList(
                                            new MediaObject()
                                                    .setName(contentName)
                                                    .setContentUrl(contentUrl)
                                                    .setIcon(new Image().setUrl("https://storage.googleapis.com/automotive-media/album_art.jpg")))))
                    .setMediaType("AUDIO"));
    responseBuilder.addSuggestions(new String[]{"Faster","Slower"});
    return responseBuilder.build();
  }

  @ForIntent("Backing Track Intent - slower")
  public ActionResponse backingTrackSlower(ActionRequest request) {
    ResponseBuilder responseBuilder = getResponseBuilder(request);
    Map<String, Object> conversationData = request.getConversationData();
    String genre = (String) conversationData.get("GENRE");
    ResourceBundle responses = ResourceBundle.getBundle("responses");
    String contentUrl;
    String contentName;

    switch (genre){
      case "jazz":
        contentUrl = responses.getString("jazz_url_slower");
        contentName = responses.getString("jazz_name");
        break;
      case "rock":
        contentUrl = responses.getString("rock_url_slower");
        contentName = responses.getString("rock_name");
        break;
      default:
        throw new IllegalStateException("Unexpected value: " + genre);
    }

    responseBuilder
            .add(responses.getString("encouragement"))
            .add(new MediaResponse()
                    .setMediaObjects(
                            new ArrayList<MediaObject>(
                                    Arrays.asList(
                                            new MediaObject()
                                                    .setName(contentName)
                                                    .setContentUrl(contentUrl)
                                                    .setIcon(new Image().setUrl("https://storage.googleapis.com/automotive-media/album_art.jpg")))))
                    .setMediaType("AUDIO"));
    responseBuilder.addSuggestions(new String[]{"Rock","Jazz"});
    return responseBuilder.build();
  }

  @ForIntent("Backing Track Intent - faster")
  public ActionResponse backingTrackFaster(ActionRequest request) {
    ResponseBuilder responseBuilder = getResponseBuilder(request);
    Map<String, Object> conversationData = request.getConversationData();
    String genre = (String) conversationData.get("GENRE");
    ResourceBundle responses = ResourceBundle.getBundle("responses");
    String contentUrl;
    String contentName;

    switch (genre){
      case "jazz":
        contentUrl = responses.getString("jazz_url_faster");
        contentName = responses.getString("jazz_name");
        break;
      case "rock":
        contentUrl = responses.getString("rock_url_faster");
        contentName = responses.getString("rock_name");
        break;
      default:
        throw new IllegalStateException("Unexpected value: " + genre);
    }

    responseBuilder
            .add(responses.getString("encouragement"))
            .add(new MediaResponse()
                    .setMediaObjects(
                            new ArrayList<MediaObject>(
                                    Arrays.asList(
                                            new MediaObject()
                                                    .setName(contentName)
                                                    .setContentUrl(contentUrl)
                                                    .setIcon(new Image().setUrl("https://storage.googleapis.com/automotive-media/album_art.jpg")))))
                    .setMediaType("AUDIO"));
    responseBuilder.addSuggestions(new String[]{"Rock","Jazz"});
    return responseBuilder.build();
  }

  @ForIntent("How Do I Chord Intent")
  public ActionResponse howDoIChord(ActionRequest request) {

    Map<String, Object> conversationData = request.getConversationData();
    String chordQuality = (String) request.getParameter("chord_quality");
    ResourceBundle responses = ResourceBundle.getBundle("responses");
    ChordObjectHolder = (List) chordData.chordStore.get(chordQuality);
    ResponseBuilder responseBuilder = getHelpResponse(request, ChordObjectHolder);
    return responseBuilder.build();
  }
  @ForIntent("How Do I Scale Intent")
  public ActionResponse howDoIScale(ActionRequest request) {

    Map<String, Object> conversationData = request.getConversationData();
    String chordQuality = (String) request.getParameter("chord_quality");
    ResponseBuilder responseBuilder = getResponseBuilder(request);
    responseBuilder.add(chordQuality);
//    ResourceBundle responses = ResourceBundle.getBundle("responses");
//    ScaleObjectHolder = (List) scalesData.scaleStore.get(chordQuality);
//    ResponseBuilder responseBuilder = getHelpResponse(request, ScaleObjectHolder);
    return responseBuilder.build();
  }

  ResponseBuilder getHelpResponse(ActionRequest request, List Object){
    ResponseBuilder responseBuilder = getResponseBuilder(request);
    responseBuilder
            .add("Hope this helps")
            .add(new BasicCard()
                    .setTitle((String) Object.get(0))
                    .setImage(new Image()
                            .setUrl((String) Object.get(1)))
                    .setButtons(
                            new ArrayList<Button>(
                                    Arrays.asList(
                                            new Button()
                                                    .setTitle("Need more info?")
                                                    .setOpenUrlAction(
                                                            new OpenUrlAction().setUrl("https://assistant.google.com"))))));
    return responseBuilder;

  }
  
}

