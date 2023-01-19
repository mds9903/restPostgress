import { Bar } from "react-chartjs-2";
import { Chart as ChartJS } from "chart.js/auto";
import { Colors } from "chart.js/auto";

ChartJS.register(Colors);
const options = {
  plugins: {
    legend: {
      display: false,
    },
  },
  maintainAspectRatio: false,
  indexAxis: "y",
};

export default function MyBarChart(props) {
  return <Bar data={props.data} options={options} />;
}
