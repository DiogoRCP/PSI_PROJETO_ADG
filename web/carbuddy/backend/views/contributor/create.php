<?php

use yii\helpers\Html;

/* @var $this yii\web\View */
/* @var $model backend\models\Contributors */
/* @var $modelUsers backend\models\Users */
/* @var $modelCompanies backend\models\Companies */

$this->title = 'Create Contributors';
$this->params['breadcrumbs'][] = ['label' => 'Contributors', 'url' => ['index']];
$this->params['breadcrumbs'][] = $this->title;
?>
<div class="contributors-create">

    <h1><?= Html::encode($this->title) ?></h1>

    <?= $this->render('_form', [
        'model' => $model, 'modelUsers' => $modelUsers, 'modelCompanies' => $modelCompanies
    ]) ?>

</div>
