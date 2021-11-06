<?php

use yii\helpers\Html;
use yii\widgets\DetailView;

/* @var $this yii\web\View */
/* @var $model frontend\models\Cars */

$this->title = $model->user->username.' ('.$model->brand.' '.$model->model.')';
\yii\web\YiiAsset::register($this);
?>
<div class="cars-view">

    <h1><?= Html::encode($this->title) ?></h1>

    <p>
        <?= Html::a('Back', ['index'], ['class' => 'btn btn-light']) ?>
        <?= Html::a('Update', ['update', 'id' => $model->id], ['class' => 'btn btn-secondary']) ?>
        <?= Html::a('Delete', ['delete', 'id' => $model->id], [
            'class' => 'btn btn-dark',
            'data' => [
                'confirm' => 'Are you sure you want to delete this item?',
                'method' => 'post',
            ],
        ]) ?>
    </p>

    <?= DetailView::widget([
        'model' => $model,
        'attributes' => [
            'id',
            'vin',
            'brand',
            'model',
            ['label' => 'Color',
                'attribute' => 'image',
                'format' => 'raw',
                'value' => $model->veicleImage()
            ],
            'carType',
            'displacement',
            'fuelType',
            'registration',
            'modelyear',
            'kilometers',
            'state',
            'userId',
        ],
    ]) ?>

</div>
