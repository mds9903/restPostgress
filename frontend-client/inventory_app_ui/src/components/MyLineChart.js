import { Line } from "react-chartjs-2";
import { Chart as ChartJS } from "chart.js/auto";
import { Colors } from "chart.js/auto";
import ChartDataLabels from "chartjs-plugin-datalabels";

ChartJS.register(Colors);
ChartJS.register(ChartDataLabels);

const onClickHandler = (e) => console.log("click on line chart");

const options = {
  onClick: onClickHandler,
  scales: {
    y: {
      ticks: {
        beginAtZero: true,
      },
    },
  },
  plugins: {
    datalabels: {
      // This code is used to display data values
      anchor: "inside",
      align: "center",
      color: "black",
    },
    legend: {
      display: false,
    },
  },
};

export default function MyLineChart(props) {
  console.log(props.data);
  return <Line height={props.height} data={props.data} options={options} />;
}
