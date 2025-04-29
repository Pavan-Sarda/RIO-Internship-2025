fetch("http://localhost:8080/responses")
  .then((response) => {
    // Check if the response is ok (status code 200)
    if (!response.ok) {
      throw new Error("Network response was not ok: " + response.statusText);
    }
    return response.json();
  })
  .then((data) => {
    console.log("Fetched Data:", data);  // Log entire data to inspect it

    if (data.length === 0) {
      console.log("No survey data available.");
      return;
    }

    let labels = [];
    let values = [];

    // Create a mapping for ratings to numeric values
    const ratingMap = {
      'Highly Satisfied': 5,
      'Satisfied': 4,
      'Neutral': 3,
      'Dissatisfied': 2,
      'Highly Dissatisfied': 1
    };

    // Loop through the fetched data and prepare labels and numeric values for the chart
    data.forEach((item) => {
      labels.push(item.question);
      values.push(ratingMap[item.rating]);  // Convert rating to numeric value
    });

    console.log("Labels:", labels);
    console.log("Values:", values);

    // Dynamically generate background colors based on the number of items
    const backgroundColors = values.map(() => {
      const randomColor = `hsl(${Math.random() * 360}, 70%, 60%)`; // Generate random color
      return randomColor;
    });

    const ctx = document.getElementById("surveyChart").getContext("2d");

    // Create a bar chart using Chart.js
    new Chart(ctx, {
      type: "bar",
      data: {
        labels: labels,
        datasets: [
          {
            label: "Satisfaction Ratings",
            data: values,
            backgroundColor: backgroundColors, // Use the dynamically generated colors
            borderColor: "black", // Optional: Add a border color for better visibility
            borderWidth: 1,
          },
        ],
      },
      options: {
        responsive: true,
        scales: {
          y: {
            beginAtZero: true, // Ensures the y-axis starts at 0
            ticks: {
              stepSize: 1, // Customize tick steps to be 1
            },
          },
        },
      },
    });
  })
  .catch((error) => {
    console.error("Error fetching data:", error);
    alert("There was an issue fetching data. Please try again later.");
  });
