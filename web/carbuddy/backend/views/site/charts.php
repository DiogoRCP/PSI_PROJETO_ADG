<?php

use dosamigos\chartjs\ChartJs;

function chart($type, $titulo, $dados, $valores)
{
    return ChartJs::widget([
        'type' => $type,
        'options' => [
            'height' => 400,
            'width' => 400,
        ],
        'data' => [
            'labels' => $dados,
            'datasets' => [
                [
                    'label' => $titulo,
                    'backgroundColor' => ["#38A3A5", "#57CC99", "#80ED99", "#C7F9CC", "#DB5461", "#F1EDEE", "#B4A6AB", "#C8B8DB", "#F9F4F5"],
                    'borderColor' => "white",
                    'pointBackgroundColor' => ["#38A3A5", "#57CC99", "#80ED99", "#C7F9CC", "#DB5461", "#F1EDEE", "#B4A6AB", "#C8B8DB", "#F9F4F5"],
                    'pointBorderColor' => "#fff",
                    'pointHoverBackgroundColor' => "#fff",
                    'pointHoverBorderColor' => "rgba(179,181,198,1)",
                    'data' => $valores
                ],
            ]
        ],
        'clientOptions' => [
            'legend' => [
                'display' => true,
                'position' => 'bottom',
                'labels' => [
                    'fontSize' => 14,
                    'fontColor' => "white",
                ]
            ]
        ]
    ]);
}

?>