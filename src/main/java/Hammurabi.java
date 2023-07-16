//package hammurabi;               // package declaration

import java.util.Random;         // imports go here
import java.util.Scanner;

public class Hammurabi {         // must save in a file named Hammurabi.java
	final int requiredFoodPerPerson = 20;
	final int personPerAcreFarmed = 10;
	final int grainNeededToFarm = 2;
	Random rand = new Random();  // this is an instance variable
	Scanner scanner = new Scanner(System.in);
	final String yearReport = "\nHAMURABI:  I BEG TO REPORT TO YOU,\n\tIN YEAR %d";
	final String starvingReport = ", %d PEOPLE STARVED";
	final String immigrantsReport = ", %d CAME TO THE CITY";
	final String endYearReport = ".\n";
	final String plagueReport = "A HORRIBLE PLAGUE STRUCK!  HALF THE PEOPLE DIED.\n";
	final String populationReport = "\tPOPULATION IS NOW %d.\n";
	final String acresReport = "\tTHE CITY NOW OWNS %d ACRES.\n";
	final String harvestReport = "YOU HARVESTED %d BUSHELS AT %d BUSHELS PER ACRE.\n";
	final String ratsReport = "RATS ATE %d BUSHELS.\n";
	final String storageReport = "\tYOU NOW HAVE %d BUSHELS IN STORE\n\n";
	final String stockMarket = "LAND IS TRADING AT %d BUSHELS PER ACRE.\n";
	final String youSuck = "DUE TO THIS EXTREME MISMANAGEMENT YOU HAVE NOT ONLY\n" +
			"BEEN IMPEACHED AND THROWN OUT OF OFFICE BUT YOU HAVE\n" +
			"ALSO BEEN DECLARED PERSONA NON GRATA!!\n";
	final String finalOverview = "\nIN YOUR 10-YEAR TERM OF OFFICE, %d PERCENT OF THE\n" +
			"POPULATION STARVED PER YEAR ON AVERAGE, I.E., A TOTAL OF\n" +
			"%d PEOPLE DIED!!\n YOU STARTED WITH 10 ACRES PER PERSON AND ENDED WITH\n" +
			"%.2f ACRES PER PERSON\n\n";
	final String tyranny = "YOUR HEAVY-HANDED PERFORMANCE SMACKS OF NERO AND IVAN IV.\n" +
			"THE PEOPLE (REMAINING) FIND YOU AN UNPLEASANT RULER, AND,\n" +
			"FRANKLY, HATE YOUR GUTS!";
	final String mediocre = "YOUR PERFORMANCE COULD HAVE BEEN SOMEWHAT BETTER, BUT\n" +
			"REALLY WASN'T TOO BAD AT ALL.\n %d PEOPLE WOULD" +
			"DEARLY LIKE TO SEE YOU ASSASSINATED BUT WE ALL HAVE OUR TRIVIAL PROBLEMS";
	final String idolizeYou = "A FANTASTIC PERFORMANCE!!!  CHARLEMANGE, DISRAELI, AND\n" +
			"JEFFERSON COMBINED COULD NOT HAVE DONE BETTER!";
	final String ttfn = "\n\n\n\n\n\nSo long for now.";

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
		int populationDecrease = -5, harvested = 3000, hungryRats = 200, totalStarved = 0;
		int acresBought, foodStock, harvestingAcres = 1000, plaguedToDeath, percentDied = 0;

		// statements go after the declarations
		// main gameplay loop
		for(int year = 1; year < 10; year++){
			String report = generateSummary(populationDecrease, harvested, harvestingAcres, plague, hungryRats);
			printSummary(report, year, population, acres, grain, priceOfLand);

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
			grain -= grainNeededToFarm * harvestingAcres;

			// BACKEND CALCULATION TIME
			// do the population calculations first
			plaguedToDeath = plagueDeaths(population);
			plague = (plaguedToDeath > 0);
			population -= plaguedToDeath;

			populationDecrease = starvationDeaths(population, foodStock);
			totalStarved += populationDecrease;
			// TEST FOR UPRISING
			if(uprising(population, populationDecrease)){
				epicFail(1, populationDecrease);
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
			percentDied = (year * percentDied + totalStarved * 100 / population) / (year + 1);
		}

		finalSummary(population, acres, totalStarved, percentDied);
	}

	// other methods go here
	// all these here are about printing things/returning strings
	String generateSummary(int decreasePop, int harvestedFood, int harvestingAcres, boolean plague, int rats){
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
		if(harvestingAcres > 0) {
			sb.append(String.format(harvestReport, harvestedFood, harvestedFood/harvestingAcres));
		}
		if(rats > 0){
			sb.append(String.format(ratsReport, rats));
		}
		sb.append(storageReport);
		sb.append(stockMarket);
		return sb.toString();
	}
	private void printSummary(String report, int year, int population, int acres, int grain, int priceOfLand){
		System.out.printf(report, year, population, acres, grain, priceOfLand);
	}
	private void epicFail(int x, Integer deaths) {
		StringBuilder sb = new StringBuilder();
		switch (x) {
			case 0:
				sb.append("HAMURABI:  I CANNOT DO WHAT YOU WISH.\nGET YOURSELF ANOTHER STEWARD!!!!!");
				break;
			case 1:
				sb.append(String.format("\nYOU STARVED %d PEOPLE IN ONE YEAR!!!\n", deaths));
				sb.append(youSuck);
				break;
		}
		System.out.println(sb.toString());
		System.exit(0);
	}
	private void youPoorBro(int bushels) {
		System.out.printf("HAMURABI:  THINK AGAIN. YOU HAVE ONLY %d BUSHELS OF GRAIN. NOW THEN,", bushels);
	}
	private void youGotNoLand(int acres) {
		System.out.printf("HAMURABI:  THINK AGAIN. YOU OWN ONLY %d ACRES. NOW THEN,", acres);
	}
	private void notEnoughFriends(int friends) {
		System.out.printf("HAMURABI:  THINK AGAIN. BUT YOU HAVE ONLY %d PEOPLE TO TEND THE FIELDS. NOW THEN,", friends);
	}
	private void finalSummary(int population, int acres, int totalDeaths, int percentDied) {
		StringBuilder sb = new StringBuilder();
		double acresPerPerson = (double) acres / population;
		sb.append(String.format(finalOverview, percentDied, totalDeaths, acresPerPerson));
		if (percentDied > 33 || acresPerPerson < 7) {
			sb.append(youSuck);
		}
		else if (percentDied > 10 || acresPerPerson < 9) {
			sb.append(tyranny);
		}
		else if (percentDied > 3 || acres / population < 10) {
			sb.append(String.format(mediocre, (int)(rand.nextDouble() * population * .8)));
		}
		else {
			sb.append(idolizeYou);
		}
		sb.append(ttfn);
		System.out.println(sb.toString());
	}

	// asking the user some questions
	int askHowManyAcresToBuy(int price, int bushels){
		int result;
		do {
			System.out.print("\nHOW MANY ACRES DO YOU WISH TO BUY?  ");
			result = scanner.nextInt();
			if (result < 0 || result * price > bushels) {
				youPoorBro(bushels);
			}
		} while (result < 0 || result * price > bushels);
		return result;
	}
	int askHowManyAcresToSell(int acresOwned){
		int result;
		do {
			System.out.print("HOW MANY ACRES DO YOU WISH TO SELL?  ");
			result = scanner.nextInt();
			if (result < 0) {
				epicFail(0, null);
			}
			if (result > acresOwned) {
				youGotNoLand(acresOwned);
			}
		} while (result > acresOwned);
		return result;
	}
	int askHowMuchGrainToFeedPeople(int bushels){
		int result;
		do {
			System.out.print("\nHOW MANY BUSHELS DO YOU WISH TO FEED YOUR PEOPLE?  ");
			result = scanner.nextInt();
			if (result < 0) {
				epicFail(0, null);
			}
			if (result > bushels) {
				youPoorBro(bushels);
			}
		} while (result > bushels);
		return result;
	}
	int askHowManyAcresToPlant(int acresOwned, int population, int bushels){
		int result;
		do {
			System.out.print("\nHOW MANY ACRES DO YOU WISH TO PLANT WITH GRAIN?  ");
			result = scanner.nextInt();
			if (result < 0) {
				epicFail(0, null);
			}
			else if (result > acresOwned) {
				youGotNoLand(acresOwned);
			}
			else if (result * grainNeededToFarm > bushels) {
				youPoorBro(bushels);
			}
			else if (result > population * personPerAcreFarmed) {
				notEnoughFriends(population);
			}
		} while (result > acresOwned || result * grainNeededToFarm > bushels || result > population * personPerAcreFarmed);
		return result;
	}

	// backend calculations
	int plagueDeaths(int population){
		return (rand.nextDouble() < 0.15) ? (population / 2) : 0;
	}
	int starvationDeaths(int population, int bushelsFedToPeople){
		return (bushelsFedToPeople > population * requiredFoodPerPerson) ? 0 : (population - bushelsFedToPeople / requiredFoodPerPerson);
	}
	boolean uprising(int population, int howManyPeopleStarved){
		return ((double) howManyPeopleStarved / population > 0.45);
	}
	int immigrants(int population, int acresOwned, int grainInStorage){
		return (20 * acresOwned + grainInStorage) / (100 * population) + 1;
	}
	int harvest(int acres){
		// Choose an integer 1 to 6 inclusive - this is your yield
		int yield = rand.nextInt(6) + 1;
		return yield * acres;
	}
	int grainEatenByRats(int bushels){
		double ratRate = rand.nextDouble() / 5 + 0.1;
		return (rand.nextDouble() < 0.40) ? (int) (bushels * ratRate) : 0;
	}
	int newCostOfLand(){
		return rand.nextInt(7) + 17;
	}
}