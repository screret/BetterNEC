package me.mindlessly.notenoughcoins.utils;

import static me.mindlessly.notenoughcoins.utils.Utils.getJson;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
import me.mindlessly.notenoughcoins.commands.Flip;

public class ApiHandler {

	public static void getBins(HashMap<String, Double> dataset) {
		try {
			JsonObject binJson = getJson("https://moulberry.codes/lowestbin.json").getAsJsonObject();
			for (Map.Entry<String, JsonElement> auction : binJson.entrySet()) {
				dataset.put(auction.getKey(), auction.getValue().getAsDouble());
			}
		} catch (Exception e) {
			Reference.logger.error(e.getMessage(), e);
		}
	}

	public static String getUuid(String name) {
		try {
			return getJson("https://api.mojang.com/users/profiles/minecraft/" + name).getAsJsonObject().get("id").getAsString();
		} catch (Exception e) {
			Reference.logger.error(e.getMessage(), e);
			return null;
		}
	}

	public static void updatePurseCoins(String key, String name) {
		String uuid = getUuid(name);

		try {
			JsonArray profilesArray = getJson("https://api.hypixel.net/skyblock/profiles?key=" + key + "&uuid=" + uuid)
				.getAsJsonObject()
				.get("profiles")
				.getAsJsonArray();

			// Get last played profile
			int profileIndex = 0;
			Instant lastProfileSave = Instant.EPOCH;
			for (int i = 0; i < profilesArray.size(); i++) {
				Instant lastSaveLoop;
				try {
					lastSaveLoop =
						Instant.ofEpochMilli(
							profilesArray
								.get(i)
								.getAsJsonObject()
								.get("members")
								.getAsJsonObject()
								.get(uuid)
								.getAsJsonObject()
								.get("last_save")
								.getAsLong()
						);
				} catch (Exception e) {
					continue;
				}

				if (lastSaveLoop.isAfter(lastProfileSave)) {
					profileIndex = i;
					lastProfileSave = lastSaveLoop;
				}
			}

			Flip.purse =
				profilesArray
					.get(profileIndex)
					.getAsJsonObject()
					.get("members")
					.getAsJsonObject()
					.get(uuid)
					.getAsJsonObject()
					.get("coin_purse")
					.getAsDouble();
		} catch (Exception e) {
			Reference.logger.error(e.getMessage(), e);
		}
	}
}
