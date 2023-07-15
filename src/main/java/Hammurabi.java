//package hammurabi;               // package declaration

import java.util.ArrayList;
import java.util.Random;         // imports go here
import java.util.Scanner;

public class Hammurabi {         // must save in a file named Hammurabi.java
	Random rand = new Random();  // this is an instance variable
	Scanner scanner = new Scanner(System.in);
	String yearReport = "\nHAMURABI:  I BEG TO REPORT TO YOU,\n\tIN YEAR %d";
	String starvingReport = ", %d PEOPLE STARVED";
	String immigrantsReport = ", %d CAME TO THE CITY";
	String endYearReport = ".\n";
	String plagueReport = "A HORRIBLE PLAGUE STRUCK!  HALF THE PEOPLE DIED.\n";
	String populationReport = "\tPOPULATION IS NOW %d.\n";
	String acresReport = "\tTHE CITY NOW OWNS %d ACRES.\n";
	String harvestReport = "YOU HARVESTED %d BUSHELS PER ACRE.\n";
	String ratsReport = "RATS ATE %d BUSHELS.\n";
	String storageReport = "\tYOU NOW HAVE %d BUSHELS IN STORE\n\n";
	String stockMarket = "LAND IS TRADING AT %d BUSHELS PER ACRE.\n";

	public static void main(String[] args) { // required in every Java program
		new Hammurabi().playGame();
	}

	void playGame() {
		// WELCOME TO THE GAME YOU PEASANT
		System.out.println("\t\t\t\tHAMURABI\n\t       CREATIVE COMPUTING MORRISTOWN, NEW JERSEY\n\n" +
				"TRY YOUR HAND AT GOVERNING ANCIENT SUMERIA\nSUCCESSFULLY FOR A TEN-YEAR TERM OF OFFICE.");

		// declare local variables here: grain, population, etc.
		// Initial conditions
		int population = 100, grain = 2800, acres = 1000, priceOfLand = 19;

		// some booleans I might use
		boolean plague = false;

		// other important stats
		int populationDecrease = 0, harvested = 0, hungryRats = 0;
		int acresBought, foodStock, harvestingAcres;

		// statements go after the declarations
		// main gameplay loop
		for(int year = 0; year < 10; year++){
			String report = generateReport(populationDecrease, harvested, plague, hungryRats);
			System.out.printf(report, year, population, acres, harvested, grain, priceOfLand);

			// ask the user about their land
			acresBought = askHowManyAcresToBuy(priceOfLand, grain);
			if(acresBought == 0) {
				acresBought = -1 * askHowManyAcresToSell(acres);
			}
			// UPDATE ACRES AND GRAIN
			acres += acresBought;
			grain -= acresBought * priceOfLand;

			// ask the user about feeding people
			foodStock = askHowMuchGrainToFeedPeople(grain);
			grain -= foodStock;

			// ask the user about planting crops
			harvestingAcres = askHowManyAcresToPlant(acres, population, grain);
			// NOT SURE WHAT TO UPDATE HERE BUT I NEED TO UPDATE SOMETHING

			// BACKEND CALCULATION TIME
			// start with calculations about the people
			if(plagueDeaths(population) > 0){
				plague = true;
				population /= 2;
			}
			populationDecrease = starvationDeaths(population, foodStock);
			// TEST FOR UPRISING
			if(uprising(population, populationDecrease)){
				break;
			}
			if(populationDecrease == 0) {
				populationDecrease = -1 * immigrants(population, acres, grain);
			}
			// UPDATE POPULATION
			population -= populationDecrease;

			// next do calculations about the grain storage
			harvested = harvest(harvestingAcres);
			grain += harvested;
			hungryRats = grainEatenByRats(grain);

			// lastly calculate the new cost of land
			priceOfLand = newCostOfLand();
		}
	}

	// other methods go here
	String generateReport(int decreasePop, int harvestedFood, boolean plague, int rats){
		StringBuilder sb = new StringBuilder();
		sb.append(yearReport);
		if(decreasePop > 0){
			sb.append(String.format(starvingReport, decreasePop));
		}
		if (decreasePop < 0){
			sb.append(String.format(immigrantsReport, -1 * decreasePop));
		}
		sb.append(endYearReport);
		if(plague){
			sb.append(plagueReport);
		}
		sb.append(populationReport);
		sb.append(acresReport);
		if(harvestedFood > 0) {
			sb.append(String.format(harvestReport, harvestedFood));
		}
		if(rats > 0){
			sb.append(String.format(ratsReport, rats));
		}
		sb.append(storageReport);
		sb.append(stockMarket);
		return sb.toString();
	}

	// asking the user some questions
	int askHowManyAcresToBuy(int price, int bushels){
		return 0;
	}
	int askHowManyAcresToSell(int acresOwned){
		return 0;
	}
	int askHowMuchGrainToFeedPeople(int bushels){
		return 0;
	}
	int askHowManyAcresToPlant(int acresOwned, int population, int bushels){
		return 0;
	}

	// backend calculations
	int plagueDeaths(int population){
		return 0;
	}
	int starvationDeaths(int population, int bushelsFedToPeople){
		return 0;
	}
	boolean uprising(int population, int howManyPeopleStarved){
		return false;
	}
	int immigrants(int population, int acresOwned, int grainInStorage){
		return 0;
	}
	int harvest(int acres){
		return 0;
	}
	int grainEatenByRats(int bushels){
		return 0;
	}
	int newCostOfLand(){
		return 0;
	}
}