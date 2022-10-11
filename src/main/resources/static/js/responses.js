    function getBotResponse(input) {
    
    if (input == "Hi") {
        return "Hi..How Can I Help You";
    } else if (input == "which courses offered by University") {
        return "1)BSC Computer Science" +
                "2)Bachelor of Business adminiStration";
    } else if (input == "Placement ratio") {
        return "Placement Ratio= 90% and Above";
    }
    else if (input == "Please share Fees Information of Courses") {
        return "BSC Computer Science= 60000/- and 2)Bachlor of Business adminiStration=65000/-";
    }
    else if (input == "What Are the Facilities?") {
        return "Library,Workshop,Scholership,Canteen,Hostel";
    }
     else if (input == "infomation about Scholarship") {
        return "Are u Applied for Scholarship yes or no";
    }
     else if (input == "yes") {
        return "for more infomation please contact to us helpline Number-220-450691";
    }
     else if (input == "No") {
        return "Ok";
    }
    else {
        return "Try asking something else!";
     }

}