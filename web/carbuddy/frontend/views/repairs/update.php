<?php

use yii\helpers\Html;

/* @var $this yii\web\View */
/* @var $model frontend\models\Repairs */

$this->title = 'Update Repairs: ' . $model->id;
//$this->params['breadcrumbs'][] = ['label' => 'Repairs', 'url' => ['index']];
//$this->params['breadcrumbs'][] = ['label' => $model->id, 'url' => ['view', 'id' => $model->id]];
//$this->params['breadcrumbs'][] = 'Update';
?>
<div class="repairs-update">
    <div class="repairs-content">
    <h1><?= Html::encode($this->title) ?></h1>

    <?= $this->render('_form', [
        'model' => $model,
    ]) ?>
    </div>
</div>
